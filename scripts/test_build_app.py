"""Check app metadata and packaging without invoking Maven or jpackage."""

import os
from pathlib import Path
import tempfile
import unittest
from unittest import mock
import xml.etree.ElementTree as ET

import build_app


class BuildAppTests(unittest.TestCase):
    def setUp(self):
        arguments = mock.patch.object(build_app.sys, 'argv', ['build_app.py'])
        arguments.start()
        self.addCleanup(arguments.stop)

    def test_app_version_matches_pom_revision_and_zip_name_has_no_version(self):
        with tempfile.TemporaryDirectory() as directory:
            jdk = Path(directory)
            (jdk / 'bin').mkdir()
            (jdk / 'bin' / 'jpackage').touch()
            (jdk / 'release').write_text('OS_ARCH="amd64"\n')
            with mock.patch.dict(os.environ, {'JAVA_HOME': str(jdk)}), \
                    mock.patch.object(build_app.sys, 'platform', 'linux'), \
                    mock.patch.object(build_app.subprocess, 'run') as run, \
                    mock.patch.object(build_app, 'ZipFile') as archive, \
                    mock.patch.object(build_app.shutil, 'rmtree'), \
                    mock.patch.object(Path, 'unlink'):
                build_app.main()
            jpackage_command = run.call_args_list[1].args[0]
            root = Path(build_app.__file__).resolve().parents[1]
            pom = ET.parse(root / 'pom.xml')
            revision = pom.findtext('{*}properties/{*}revision')
            version_index = jpackage_command.index('--app-version') + 1
            self.assertEqual(jpackage_command[version_index], revision)
            self.assertEqual(archive.call_args.args[0].name, 'jfxtris-linux-x64.zip')

    def test_missing_java_home_fails_before_commands(self):
        with mock.patch.dict(os.environ, {}, clear=True), \
                self.assertRaisesRegex(SystemExit, 'Set JAVA_HOME'):
            build_app.main()

    def test_missing_jpackage_fails_before_commands(self):
        with tempfile.TemporaryDirectory() as directory, \
                mock.patch.dict(os.environ, {'JAVA_HOME': directory}), \
                self.assertRaisesRegex(SystemExit, 'jpackage not found'):
            build_app.main()


if __name__ == '__main__':
    unittest.main()
