# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [8.3.2] - 2023-09-16

### Added

- added java/maven badges

### Changed

- fj-daogen-version set to 1.2.2 in module fj-core-jvfs
- ImplFinder interface methods does not throw any exception now

## [8.3.1] - 2023-09-15

### Changed

- fj-bom updated to 1.4.0 (fj-test-helper8 now managed by parent pom)

## [8.3.0] - 2023-09-15

### Changed

- fj-bom updated to 1.3.8 [Apache Commons Compress denial of service vulnerability](https://github.com/fugerit-org/fj-lib/security/dependabot/7)
- fj-test-helper8 to 0.5.0
- MetaDataUtils types constant arrays removed in favor of public builder methods (may raise minor compatibility issues)

### Fixed

- Javadoc generation with java 17
- VirtualPageCache entry store did not work properly

### Security

- [Apache Commons Compress denial of service vulnerability](https://github.com/fugerit-org/fj-lib/security/dependabot/7)

## [8.2.8] - 2023-09-13

### Changed

- HelperIOException now exrends IOException

## [8.2.7] - 2023-09-11

### Added

- Api for SafeFunctions handling
- [workflow codeql on branch main](.github/workflows/codeql-analysis.yml)
- [workflow deploy on branch main](.github/workflows/deploy_maven_package.yml)
- [dependabot](.github/dependabot.yml) configuration

### Changed

- fj-bom updated to to 1.3.6
- better test coverage

### Fixed

- ArgUtils flag (param without values) are now read to default value of '1'

## [8.2.6] - 2023-09-04

### Changed

- better coverage of BinaryCalc

### Security

- fj-bom updated to to 1.3.5

## [8.2.5] - 2023-09-04

### Added

- [workflow](src/main/md/github/create_maven_build_workflow.md) for package testing and dependency upload

### Changed

- fj-bom set to 1.3.4
- fj-daogen test dependency set to 1.1.9
- reference to https://keepachangelog.com/ v1.1.0 in changelog

## [8.2.4] - 2023-09-04

### Added

- schema-catalog-config-1-0.xsd for improved coverage (#21)
- added tag element (HEAD) to scm element. (pom.xml)
- added issueManagement element (pom.xml, url : https://github.com/fugerit-org/fj-lib/issues )

### Changed

- fj-bom set to 1.3.3
- Changelog badge link set absolute 'https://github.com/fugerit-org/fj-lib/blob/main/CHANGELOG.md'
- fj-tester-helper8 set to 0.4.1
- Improved coverage and serialization for BasicValidator XMLSchemaValidatorConifg, FactoryCatalog (#21).

### Fixed

- TreeConfigXML didn't check if multiple root node where present (#21).
- Set the license url in the property 'licenseURL' as in the 'license' tag.
- scm url (.git was missing at the end).

## [8.2.3] - 2023-08-31

### Added

- javadoc badges
- modules description

### Changed

- fj-bom version set to 1.3.1
- Sonar Cloud Maven Build set to use maven profile sonarfugerit and github environmental variable for sonarKey

### Removed

- index.md

## [8.2.2] - 2023-08-31

### Fixed

- ClassHelper.newInstance(String) no longer throws exception (#23).
- fixed-config-extract, it previously updated the source xlsx.

### Changed

- Updated module fj-core-jvfs dependancy fj-daogen-maven-plugin to 1.1.6

## [8.2.1 and previous]

### Changed

- only the [release notes](docgen/release-notes.txt) are available.