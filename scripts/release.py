#!/usr/bin/env python3
"""Tag releases after master passes its builds and prepare release notes."""

import os
from pathlib import Path
import re
import subprocess
import sys
import xml.etree.ElementTree as ET

ROOT = Path(__file__).resolve().parent.parent
NOTES = ROOT / 'release-notes.md'
ORIGINAL_PROJECT_TAG = 'university-original'
SEMVER = re.compile(r'(0|[1-9][0-9]*)\.(0|[1-9][0-9]*)\.(0|[1-9][0-9]*)')
BOT_NAME = 'github-actions[bot]'
BOT_EMAIL = '41898282+github-actions[bot]@users.noreply.github.com'


def validate_version(version):
    """Accept only bare numeric release versions."""
    if not SEMVER.fullmatch(version):
        raise ValueError(f'Invalid release version: {version!r}')
    return version


def run(*args):
    """Run a command as the release bot and return its output."""
    env = {**os.environ,
           'GIT_AUTHOR_NAME': BOT_NAME, 'GIT_AUTHOR_EMAIL': BOT_EMAIL,
           'GIT_COMMITTER_NAME': BOT_NAME, 'GIT_COMMITTER_EMAIL': BOT_EMAIL}
    try:
        return subprocess.run(args, cwd=ROOT, env=env, check=True,
                              capture_output=True, text=True).stdout.strip()
    except subprocess.CalledProcessError as error:
        if error.stderr:
            print(error.stderr, file=sys.stderr, end='')
        raise


def checks_passed(sha):
    """Check the latest Standalone builds push run for this commit."""
    endpoint = ('repos/{owner}/{repo}/actions/workflows/standalone.yml/runs'
                f'?branch=master&event=push&head_sha={sha}&per_page=100')
    query = f'''
        [.workflow_runs[] | select(
            .name == "Standalone builds" and
            .head_sha == "{sha}" and
            .event == "push"
        )]
        | max_by(.id)
        | .status == "completed" and .conclusion == "success"
    '''
    return run('gh', 'api', endpoint, '--jq', query) == 'true'


def release_exists(version):
    """Return whether this version has a published release, not a draft."""
    try:
        return run('gh', 'release', 'view', version, '--json', 'isDraft',
                   '--jq', '.isDraft') == 'false'
    except subprocess.CalledProcessError as error:
        if not any(message in (error.stderr or '')
                   for message in ('release not found', 'HTTP 404')):
            raise
        return False


def output(version, notes):
    """Save the notes and pass the version to the build job."""
    NOTES.write_text(notes + '\n', encoding='utf-8')
    with open(os.environ['GITHUB_OUTPUT'], 'a', encoding='utf-8') as stream:
        stream.write(f'version={version}\n')
    print(f'Prepared release {version}.')


def recover_release(version, tags):
    """Prepare another build if a tagged version was never published."""
    if release_exists(version):
        print(f'Release {version} already exists.')
        return
    previous_tag = tags[1] if len(tags) > 1 else ORIGINAL_PROJECT_TAG
    notes = run('git-cliff', f'{previous_tag}..HEAD', '--strip', 'all')
    output(version, notes)


def first_release():
    """Use the pom version and leave the original project out of the notes."""
    pom = ET.parse(ROOT / 'pom.xml')
    version = validate_version(pom.findtext('{*}properties/{*}revision'))
    notes = run('git-cliff', f'{ORIGINAL_PROJECT_TAG}..HEAD',
                '--tag', version, '--strip', 'all')
    return version, notes


def bump_pom_revision(version):
    """Update the revision property without reformatting the pom."""
    pom = ROOT / 'pom.xml'
    text = pom.read_text(encoding='utf-8')
    text, count = re.subn(r'<revision>[^<]*</revision>',
                          f'<revision>{version}</revision>', text)
    if count != 1:
        raise ValueError('Expected one revision property in pom.xml')
    pom.write_text(text, encoding='utf-8')


def next_release():
    """Choose the next version from commits and commit the pom update."""
    version = validate_version(run('git-cliff', '--bumped-version'))
    notes = run('git-cliff', '--unreleased', '--bump', '--strip', 'all')
    bump_pom_revision(version)
    run('git', 'add', 'pom.xml')
    run('git', 'commit', '-m', f'release: {version}')
    return version, notes


def push_release(version):
    """Push the release tag and master together, or neither."""
    run('git', 'tag', version)
    run('git', 'push', '--atomic', 'origin', 'HEAD:master', version)


def main():
    """Release the latest master commit once its platform builds pass."""
    run('git', 'fetch', 'origin', 'master', '--tags')
    run('git', 'checkout', '--detach', 'origin/master')
    tags = [tag for tag in run('git', 'tag', '--merged', 'HEAD',
                              '--sort=-version:refname').splitlines()
            if SEMVER.fullmatch(tag)]
    head_tags = run('git', 'tag', '--points-at', 'HEAD').splitlines()
    for tag in tags:
        if tag in head_tags:
            recover_release(tag, tags)
            return
    if not checks_passed(run('git', 'rev-parse', 'HEAD')):
        print('The latest Standalone builds push run for master has not passed. Nothing to release.')
        return
    if tags and run('git', 'rev-list', '--count', f'{tags[0]}..HEAD') == '0':
        print('No commits since the last release.')
        return
    if tags:
        version, notes = next_release()
    else:
        version, notes = first_release()
    push_release(version)
    output(version, notes)


if __name__ == '__main__':
    main()
