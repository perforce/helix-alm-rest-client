![Support](https://img.shields.io/badge/Support-Community-yellow.svg)
# Helix ALM REST Client API
## Overview
The halm-rest-client-api is the starting point for building a complete standalone Java client that can be used to directly interact with the Helix ALM REST API. [Learn more](https://www.perforce.com/products/helix-alm) about Helix ALM.

The halm-rest-client-api is used by the [Helix ALM Jenkins plugin]() to submit test results to the Helix ALM REST API. See the [Helix ALM help](https://help.perforce.com/alm/help.php?product=helixalm&type=web&topic=JenkinsPlugin) for information about using the plugin.

Maintained by [Perforce Software](https://www.perforce.com/).

## Requirements
* [Helix ALM 2022.2 Server](https://www.perforce.com/downloads/helix-alm) or later
* [Helix ALM REST API 2022.2](https://www.perforce.com/downloads/helix-alm) or later. Installed with the Helix ALM Server.

## License
[MIT License](LICENSE.txt)

# Support
The halm-rest-client is a community supported project and is not officially supported by Perforce.

Pull requests and issues are the responsibility of the project's moderator(s); this may be a vetted individual or team
with members outside of the Perforce organization. Perforce does not officially support these projects, therefore all
issues should be reported and managed via GitHub (not via Perforce's standard support process).

# Build
To build, use:
`./gradlew.bat build`

## Versioning
When publishing, update `currentVersion` in the local project's `gradle.properties` file.

We follow semantic versioning for Major - Minor - Patch:
* Major = Breaking
* Minor = Additive
* Patch = Bugfix

## Variables
Set any required variables in `~/.gradle/gradle.properties`. Variables can also be specified via the command line:

`./gradlew.bat build -PartifactoryUsername=username -PartifactoryPassword=password`

## Testing
This project has two test modes:
* ./gradlew.bat test
* ./gradlew.bat integrationTest

### test
Runs the build in unit tests.

### integrationTest
Runs a series of integration tests with the Helix ALM REST API. This expects a Helix ALM database and projects similar to the
REST API WITS setup.
