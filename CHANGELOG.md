# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [8.6.9] - 2025-04-27

### Changed

- Migrating from "Legacy OSSRH" to "Central Portal"
- fj-bom version set to 2.0.0
- migrate from junit 4 to 5 <https://github.com/fugerit-org/fj-lib/issues/93>

## [8.6.8] - 2025-04-16

### Added

- FileIO.newFile() create utility init a new File <https://github.com/fugerit-org/fj-lib/issues/90>

## [8.6.7] - 2025-04-09

### Added

- XMLFactorySAX.newInstanceSecure() disabling external entities <https://github.com/fugerit-org/fj-lib/issues/87>

### Changed

- Added 'ubuntu-24.04-arm' runner to compatibility workflow

## [8.6.6] - 2024-11-17

### Changed

- Added method ConfigRuntimeException.convertToRuntimeEx()
- Added CloseableDAOContextAbstract.newCloseableDAOContextDS()
- Added CloseableDAOContextAbstract.newCloseableDAOContextCF()

## [8.6.5] - 2024-09-07

### Added

- SafeFunction.getUsingDefault()

## [8.6.4] - 2024-07-18

### Fixed

- Fixed oracle jdbc adapter for MetaDataUtils

## [8.6.3] - 2024-07-18

### Fixed

- MetaDataUtils.createModel with JdbcAdaptor is now public

## [8.6.2] - 2024-05-20

### Added

- LibHelper utility

## [8.6.1] - 2024-05-14

### Added

- SimpleCheckpoint utility

## [8.6.0] - 2024-05-09

### Added

- CheckDuplicationProperties and PropsUtils

## [8.5.9] - 2024-05-05

### Fixed

- typo in SQLTypeConverter.localTimeToSqlTime()

## [8.5.8] - 2024-05-05

### Changed

- support for conversion LocaTime to/from java.sql.Time

## [8.5.7] - 2024-05-05

### Changed

- support for conversion from LocalDate/LocalDateTime to Date/Timestamp

## [8.5.6] - 2024-05-05

### Changed

- support for conversion from java.util.Date to LocalDate/LocalDateTime
- fj-bom version set to 1.6.5
- DBUtils.indentifyDB() now recognizes h2 (600) and hsqldb (700) databases.
- IdGenerator for h2 is mapped to Postgres by default

## [8.5.5] - 2024-04-06

### Added

- CodeEx interface for CodeException and CodeRuntimeException
- ExUtils to handle UnsupportedOperationException

## [8.5.4] - 2024-04-02

### ArchiveIO addEntry utility (add a ZipEntry to a ZioOutputStream)

### Changed

- jvfs daogen model is not set to next generation (not serializable)
- fj-daogen-version set to 1.8.1
- fj-bom version set to 1.6.4

## [8.5.3] - 2024-03-08

### Added

- IdFinderNG

## [8.5.2] - 2024-03-08

### Added

- BasicWrapperNG with no serialization

## [8.5.1] - 2024-03-08

### Added

- ObjectIO helper for object serialization

## [8.5.0] - 2024-03-02

### Added

- reflect-config for ByteArrayDataHandler and CharArrayDataHandler

## [8.4.10] - 2024-02-28

### Changed

- fj-daogen-version set to 1.5.0
- Added publicClass attribute to SimpleJavaGenerator

## [8.4.9] - 2024-02-21

### Changed

- fj-bom set to 1.6.1

## [8.4.8] - 2024-02-12

### Added

- utilty FileIO.isInTmpFolder() to check if a file is in temp path

### Changed

- fj-bom set to 1.6.0
- review workflows
- Upgraded build_maven_package workflow to version 1.0.1, (accespt DISABLE_MAVEN_DEPENDENCY_SUBMISSION)

## [8.4.7] - 2024-01-22

### Added

- method one() (shortcut for getSingleResult) in interface DaoResultList

### Changed

- fj-daogen set to 1.3.2

## [8.4.6] - 2023-12-22

### Changed

- Added java 21 to github action workflow for compatibility check
- fj-bom parent set to 1.5.2
- fj-bom parent set to 1.5.1, [fix lombok-maven-plugin compatibility with java 21](https://github.com/fugerit-org/fj-bom/blob/main/CHANGELOG.md#151---2023-12-22)

### Fixed

- java version helper with major only version (fix for java 21)

## [8.4.5] - 2023-11-12

### Changed

- fj-bom parent set to 1.5.0

### Fixed

- javadoc generation for lombok annotated classes

## [8.4.4] - 2023-10-23

### Added

- applyWithMessage() and getWithMessage() special function to SafeFunction

## [8.4.3] - 2023-10-22

### Added

- applyWithMessage() and getWithMessage() special function to ConfigException, DAOException and HelperIOException

## [8.4.2] - 2023-10-22

### Added

- apply() and get() special function to ConfigException, DAOException and HelperIOException

### Changed

- commons-io version set to 2.14.0
- java and maven badges link

### Fixed

- LICENSE place holder

## [8.4.1] - 2023-10-01

### Added

- [SafeFunction documentation](https://jupiterdocs.fugerit.org/fj-core/src/docs/SafeFunction.html)

### Changed

- RealJFile nows creates a FileWriter and FileReader for getWriter() and getReader() methods.
- Default behavior of SafeFunction is now to wraps only checked exceptions. runtime exception are just re thrown.

### Fixed

- windows run of TestRealJFile.testFullFile()

## [8.4.0] - 2023-09-30

### Added

- transformer config facility
- xml white space remove utility
- StreamHelper.resolveReader() method

### Changed

- fj-bom version set to 1.4.7
- fj-daogen version set to 1.3.1

## [8.3.9] - 2023-09-25

### Added

- Code of conduct badge and file
- [Sample jdk compatibility check workflow on branch develop](.github/workflows/build_maven_compatibility.yml)
- Added BasicDAOResult extensions for stream() and Optional

### Changed

- [Sonar cloud workflow merged in maven build](.github/workflows/deploy_maven_package.yml)
- fj-bom version set to 1.4.5
- fj-daogen version set to 1.2.4
- jersey-server substituted by fj-test-helper-java-jaxrs dependency 

### Removed

- Sonar cloud workflow yml removed. (after being merged with maven build)

### Fixed

- dependabot workflow ecosystem 

## [8.3.8] - 2023-09-23

### Added

- IteratorHelper utils

## [8.3.7] - 2023-09-19

### Changed

- Renamed typo in SafeFunction.applyIfNotNull()
- Restored log behavior of setFromElementSafe() (will not print stack trace)

## [8.3.6] - 2023-09-18

### Changed

- Renamed typo in SafeFunction.applyIfNotNull()

## [8.3.5] - 2023-09-16

### Changed

- All Sonar Cloud issues addressed

## [8.3.4] - 2023-09-16

### Changed

- All Sonar Cloud issues addressed

## [8.3.3] - 2023-09-16

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
