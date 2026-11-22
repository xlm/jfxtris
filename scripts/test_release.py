"""External-free tests for release decisions and command failures."""

import io
import os
from pathlib import Path
import subprocess
import tempfile
import unittest
from unittest import mock

import release


class VersionTests(unittest.TestCase):
    def test_versions_require_three_plain_numbers(self):
        for version in ('0.0.0', '1.0.0', '12.34.56'):
            self.assertEqual(release.validate_version(version), version)
        for version in ('', 'v1.0.0', '01.0.0', '1.0', '1.0.0\n',
                        '１.0.0', '-1.0.0', '1.0.0-beta', '1.0.0+build',
                        '1. 0.0', '1.0.0 extra', '1x0x0'):
            with self.subTest(version=version), self.assertRaises(ValueError):
                release.validate_version(version)


class ReleaseTests(unittest.TestCase):
    def setUp(self):
        directory = tempfile.TemporaryDirectory()
        self.addCleanup(directory.cleanup)
        self.root = Path(directory.name)
        (self.root / 'pom.xml').write_text(
            '<project xmlns="http://maven.apache.org/POM/4.0.0">'
            '<properties><revision>1.0.0</revision></properties></project>')
        self.tags = ''
        self.head_tags = ''
        self.checked = 'true'
        self.commit_count = '1'
        self.bump = '1.1.0'
        self.fail = None
        self.commands = []
        for patcher in (mock.patch.object(release, 'ROOT', self.root),
                        mock.patch.object(release, 'NOTES', self.root / 'notes'),
                        mock.patch.dict(os.environ, {'GITHUB_OUTPUT': str(self.root / 'output')}),
                        mock.patch.object(release, 'run', side_effect=self.command)):
            patcher.start()
            self.addCleanup(patcher.stop)

    def command(self, *args):
        self.commands.append(args)
        if self.fail and args[:len(self.fail)] == self.fail:
            raise subprocess.CalledProcessError(1, args, stderr='network failure')
        if args[:3] == ('git', 'tag', '--merged'):
            return self.tags
        if args[:3] == ('git', 'tag', '--points-at'):
            return self.head_tags
        if args[:2] == ('git', 'rev-parse'):
            return 'abc'
        if args[:2] == ('git', 'rev-list'):
            return self.commit_count
        if args[:2] == ('gh', 'api'):
            return self.checked
        if args[:3] == ('gh', 'release', 'view'):
            return 'false'
        if args[:2] == ('git-cliff', '--bumped-version'):
            return self.bump
        if args[0] == 'git-cliff':
            return '## Notes'
        return ''

    def output(self):
        return (self.root / 'output').read_text() if (self.root / 'output').exists() else ''

    def test_first_release_tags_head_without_commit(self):
        self.tags = 'university-original\nv9.0.0'
        release.main()
        self.assertEqual(self.commands[:2], [
            ('git', 'fetch', 'origin', 'master', '--tags'),
            ('git', 'checkout', '--detach', 'origin/master')])
        self.assertIn(('git-cliff', 'university-original..HEAD', '--tag', '1.0.0', '--strip', 'all'), self.commands)
        self.assertIn(('git', 'push', '--atomic', 'origin', 'HEAD:master', '1.0.0'), self.commands)
        self.assertFalse(any(cmd[:2] == ('git', 'commit') for cmd in self.commands))
        self.assertEqual(self.output(), 'version=1.0.0\n')
        self.assertEqual((self.root / 'notes').read_text(), '## Notes\n')
        api = next(cmd for cmd in self.commands if cmd[:2] == ('gh', 'api'))
        self.assertIn('event=push&head_sha=abc', api[2])
        self.assertIn('max_by(.id)', api[4])

    def test_later_release_updates_revision_and_commits(self):
        self.tags = '1.0.0\nuniversity-original'
        release.main()
        self.assertIn('<revision>1.1.0</revision>', (self.root / 'pom.xml').read_text())
        self.assertIn(('git', 'commit', '-m', 'release: 1.1.0'), self.commands)
        self.assertIn(('git', 'tag', '1.1.0'), self.commands)
        self.assertEqual(self.output(), 'version=1.1.0\n')

    def test_release_waits_for_successful_builds(self):
        self.checked = 'false'
        release.main()
        self.assertEqual(self.output(), '')
        self.assertFalse(any(cmd[0] == 'git-cliff' for cmd in self.commands))

    def test_published_release_needs_no_further_work(self):
        self.tags = self.head_tags = '1.0.0'
        release.main()
        self.assertEqual(self.output(), '')
        self.assertFalse(any(cmd[:2] == ('gh', 'api') for cmd in self.commands))

    def test_no_new_commits_means_no_release(self):
        self.tags = '1.0.0'
        self.commit_count = '0'
        release.main()
        self.assertEqual(self.output(), '')

    def test_missing_release_recovers_first_and_later_tags(self):
        for tags, expected in (('1.0.0', 'university-original..HEAD'),
                               ('1.1.0\n1.0.0', '1.0.0..HEAD')):
            with self.subTest(tags=tags):
                self.tags = tags
                self.head_tags = tags.splitlines()[0]
                self.commands.clear()
                with mock.patch.object(release, 'release_exists', return_value=False):
                    release.main()
                self.assertIn(('git-cliff', expected, '--strip', 'all'), self.commands)
                self.assertFalse(any(cmd[:2] in (('git', 'push'), ('git', 'commit'), ('gh', 'api')) for cmd in self.commands))

    def test_failed_commands_do_not_send_a_version_to_the_build_job(self):
        for failure in (('gh', 'api'), ('git-cliff',), ('git', 'push')):
            with self.subTest(failure=failure):
                self.fail = failure
                with self.assertRaises(subprocess.CalledProcessError):
                    release.main()
                self.assertEqual(self.output(), '')

    def test_invalid_bump_does_not_edit_pom(self):
        self.tags = '1.0.0'
        self.bump = 'v1.1.0'
        before = (self.root / 'pom.xml').read_text()
        with self.assertRaises(ValueError):
            release.main()
        self.assertEqual((self.root / 'pom.xml').read_text(), before)

    def test_malformed_pom_is_not_committed(self):
        self.tags = '1.0.0'
        (self.root / 'pom.xml').write_text('<project/>')
        with self.assertRaises(ValueError):
            release.main()
        self.assertFalse(any(cmd[:2] == ('git', 'commit') for cmd in self.commands))


class CommandTests(unittest.TestCase):
    def test_command_failure_prints_stderr_and_preserves_error(self):
        for stderr in ('remote: push rejected\n', '', None):
            error = subprocess.CalledProcessError(1, 'git', stderr=stderr)
            with self.subTest(stderr=stderr), mock.patch(
                    'release.subprocess.run', side_effect=error), mock.patch(
                    'release.sys.stderr', new_callable=io.StringIO) as output:
                with self.assertRaises(subprocess.CalledProcessError) as raised:
                    release.run('git', 'push')
                self.assertIs(raised.exception, error)
                self.assertEqual(output.getvalue(), stderr or '')

    def test_missing_release_detection_survives_stderr_logging(self):
        error = subprocess.CalledProcessError(1, 'gh', stderr='HTTP 404\n')
        with mock.patch('release.subprocess.run', side_effect=error), mock.patch(
                'release.sys.stderr', new_callable=io.StringIO) as output:
            self.assertFalse(release.release_exists('1.0.0'))
            self.assertEqual(output.getvalue(), 'HTTP 404\n')

    def test_release_lookup_distinguishes_drafts(self):
        for draft, exists in (('true', False), ('false', True)):
            with self.subTest(draft=draft), mock.patch.object(
                    release, 'run', return_value=draft) as command:
                self.assertEqual(release.release_exists('1.0.0'), exists)
                command.assert_called_once_with('gh', 'release', 'view', '1.0.0',
                                                '--json', 'isDraft', '--jq', '.isDraft')

    @mock.patch('release.subprocess.run')
    def test_commands_use_bot_identity_and_return_trimmed_output(self, command):
        command.return_value.stdout = ' value\n'
        self.assertEqual(release.run('git', 'status'), 'value')
        kwargs = command.call_args.kwargs
        self.assertTrue(kwargs['check'])
        for role in ('AUTHOR', 'COMMITTER'):
            self.assertEqual(kwargs['env'][f'GIT_{role}_NAME'], release.BOT_NAME)
            self.assertEqual(kwargs['env'][f'GIT_{role}_EMAIL'], release.BOT_EMAIL)

    def test_release_lookup_only_swallows_missing(self):
        for message in ('release not found', 'HTTP 404', 'HTTP 403', 'network failure'):
            with self.subTest(message=message), mock.patch.object(release, 'run', side_effect=
                    subprocess.CalledProcessError(1, 'gh', stderr=message)):
                if message in ('release not found', 'HTTP 404'):
                    self.assertFalse(release.release_exists('1.0.0'))
                else:
                    with self.assertRaises(subprocess.CalledProcessError):
                        release.release_exists('1.0.0')


if __name__ == '__main__':
    unittest.main()
