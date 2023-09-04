# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

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