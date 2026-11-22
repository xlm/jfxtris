"""Build and zip the standalone app image for the current platform."""

import argparse
import os
import shutil
import subprocess
import sys
from pathlib import Path
from zipfile import ZipFile
import xml.etree.ElementTree as ET


def main():
    argparse.ArgumentParser(description=__doc__).parse_args()
    root = Path(__file__).resolve().parents[1]
    revision = ET.parse(root / "pom.xml").findtext("{*}properties/{*}revision")
    platform_name, image_name, launcher = {
        "linux": ("linux", "JFXTRIS", "bin/JFXTRIS"),
        "win32": ("windows", "JFXTRIS", "JFXTRIS.exe"),
        "darwin": ("macos", "JFXTRIS.app", "Contents/MacOS/JFXTRIS"),
    }[sys.platform]
    windows = platform_name == "windows"
    java_home = os.environ.get("JAVA_HOME")
    if not java_home:
        sys.exit("Set JAVA_HOME to a JDK 25 installation.")
    jdk = Path(java_home)
    jpackage = jdk / "bin" / ("jpackage.exe" if windows else "jpackage")
    if not jpackage.is_file():
        sys.exit(f"jpackage not found at {jpackage}")

    # The JDK determines the image architecture, even under emulation.
    release_lines = (jdk / "release").read_text(encoding="utf-8").splitlines()
    release = dict(line.split("=", 1) for line in release_lines)
    jdk_arch = release["OS_ARCH"].strip('"')
    arch = {"amd64": "x64", "x86_64": "x64", "aarch64": "arm64", "arm64": "arm64"}[jdk_arch]

    wrapper = ["cmd.exe", "/d", "/c", r".\mvnw.cmd"] if windows else ["./mvnw"]
    subprocess.run([*wrapper, "-B", "javafx:jlink"], cwd=root, check=True)

    target = root / "target"
    runtime = target / "runtime"
    image_dir = target / "app-image"
    image = image_dir / image_name
    if image.exists():
        shutil.rmtree(image)
    subprocess.run([
        jpackage, "--type", "app-image", "--name", "JFXTRIS",
        "--app-version", revision,
        "--dest", image_dir, "--runtime-image", runtime,
        "--module", "io.github.xlm.jfxtris/application.Main",
        "--java-options", "--enable-native-access=javafx.graphics",
    ], check=True)

    archive = target / f"jfxtris-{platform_name}-{arch}.zip"
    archive.unlink(missing_ok=True)
    if windows:
        shutil.make_archive(str(archive.with_suffix("")), "zip", image_dir, image_name)
    else:
        # Preserve executable permissions and symlinks in the app bundle.
        subprocess.run(["zip", "-qry", archive, image_name], cwd=image_dir, check=True)
    with ZipFile(archive) as contents:
        contents.getinfo(f"{image_name}/{launcher}")
    print(archive)


if __name__ == "__main__":
    main()
