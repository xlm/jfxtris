# jfxtris

A JavaFX implementation of Tetris per the official guidelines.

## Features

Guideline features:
- Vanish Zone
- BPS Random Generator
- Piece Preview
- Hold
- SRS Rotation System
- Wall Kicks
- Recursive Gravity
- Hard Drops
- Soft Drops
- Lock Delay
- Guideline Compatible Scoring System

Other:
- Multiple levels, increasing gravity
- JavaFX lighting effects!
- Basic split-screen multiplayer
- Gameplay locked at 60 FPS

Details of the guidelines implemented can be found [here](http://tetrisconcept.net/wiki/Tetris_Guideline).

## TODO

Guideline features:
- Auto Repeat System
- Music (Korobeiniki)

Other:
- Competitive (battle) multiplayer
- Ghost Piece
- Full Screen
- Options Menu for cofiguring all game parameters
- Replace UI styling code in favour of CSS
- Tidy up code between game and application packages

## Build

Install JDK 25 and set `JAVA_HOME` to point to it. The Maven wrapper downloads
Maven, JavaFX 27, and the test dependencies.

On macOS and Linux, install `unzip` before the first run so the wrapper can
unpack the checksum-verified Maven ZIP.

```sh
./mvnw test        # Compile and run the JUnit tests
./mvnw javafx:run  # Launch JFXTRIS on a desktop
```

On Windows, use `mvnw.cmd` in place of `./mvnw`.

### Standalone app image

Build on the OS you want to run the game on. With Temurin JDK 25 in
`JAVA_HOME`, install Python 3 and, on Linux or macOS, make sure `zip` and
`unzip` are available. From the repository root, run:

```sh
python3 scripts/build_app.py
```

On Windows, run `python scripts/build_app.py`. The script uses `javafx:jlink`
and `jpackage` to create the app image in `target/app-image/` and its ZIP
in `target/`. If you need a Maven mirror, configure it in your user
`~/.m2/settings.xml`.

Extract the entire ZIP, then open the launcher for your OS. The app includes
its own Java runtime.

| OS | Launcher |
| --- | --- |
| Linux | `JFXTRIS/bin/JFXTRIS` |
| Windows | `JFXTRIS/JFXTRIS.exe` |
| macOS | `JFXTRIS.app` |

Linux requires a graphical desktop with GTK 3. The Linux arm64 app also needs
glibc 2.38 or newer (for example, Ubuntu 24.04 or Debian 13).

The macOS app is distributed without notarization, so macOS may require you
to allow it in Privacy & Security.

The [Standalone builds workflow](https://github.com/xlm/jfxtris/actions/workflows/standalone.yml)
uploads ZIPs for Linux and macOS (x64 and arm64) and Windows (x64). OpenJFX
27.0.1.1 does not publish native Windows arm64 artifacts.

## Releases

To cut a release, merge conventional commits to `master`. Once Standalone
builds passes, the Release workflow writes the release notes. The very first
release uses the `<revision>` already in `pom.xml`, which is `1.0.0`.
Every later version comes from conventional commits. Never tag a release
or edit `<revision>` in `pom.xml` by hand.

The workflow builds the apps before publishing a
[GitHub Release](https://github.com/xlm/jfxtris/releases). Downloads are ZIPs
for Linux and macOS (x64 and arm64) and Windows (x64), with the version in
each filename. Local builds use a `-SNAPSHOT` suffix. CI builds use the
plain release version.

If a release fails, rerun the Release workflow in GitHub Actions. If the
latest commit on master already has a release tag but was not published,
the workflow reuses that tag and builds the apps again without bumping the
version a second time.

## Original project

The original JavaFX 2 university project is preserved at the
[`university-original`](https://github.com/xlm/jfxtris/tree/university-original) tag.

## License

[GNU General Public License, version 3](http://www.gnu.org/licenses/gpl.html)

## Copyright

- Josh Chia