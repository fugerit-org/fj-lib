# Jupiter - Fugerit Core A.P.I. fj-doc

Provides helper libraries for other java projects (io, configuration, etc.)


[![Keep a Changelog v1.1.0 badge](https://img.shields.io/badge/changelog-Keep%20a%20Changelog%20v1.1.0-%23E05735)](CHANGELOG.md) 
[![Maven Central](https://img.shields.io/maven-central/v/org.fugerit.java/fj-lib.svg)](https://mvnrepository.com/artifact/org.fugerit.java/fj-lib) 
[![license](https://img.shields.io/badge/License-Apache%20License%202.0-teal.svg)](https://opensource.org/licenses/Apache-2.0) 
[![code of conduct](https://img.shields.io/badge/conduct-Contributor%20Covenant-purple.svg)](https://github.com/fugerit-org/fj-universe/blob/main/CODE_OF_CONDUCT.md)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=fugerit-org_fj-lib&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=fugerit-org_fj-lib)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=fugerit-org_fj-lib&metric=coverage)](https://sonarcloud.io/summary/new_code?id=fugerit-org_fj-lib)

[![Java runtime version](https://img.shields.io/badge/run%20on-java%208+-%23113366.svg?style=for-the-badge&logo=openjdk&logoColor=white)](https://universe.fugerit.org/src/docs/versions/java11.html)
[![Java build version](https://img.shields.io/badge/build%20on-java%2011+-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)](https://universe.fugerit.org/src/docs/versions/java11.html)
[![Apache Maven](https://img.shields.io/badge/Apache%20Maven-3.9.0+-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)](https://universe.fugerit.org/src/docs/versions/maven3_9.html)

Useful resources : 
* [Jupiter - Fugerit Java Library Home](https://www.fugerit.org/perm/jupiter/) - Home page to the project
* [Github pages](https://jupiterdocs.fugerit.org/)

Quick start : 
 	mvn clean install
 	
Maven profiles :
- full (create javadoc and sources package too)
- test (run tests, by default tests are excluded) 

## module : [fj-core](fj-core/README.md)

[![Maven Central](https://img.shields.io/maven-central/v/org.fugerit.java/fj-core.svg)](https://mvnrepository.com/artifact/org.fugerit.java/fj-core) 
[![javadoc](https://javadoc.io/badge2/org.fugerit.java/fj-core/javadoc.svg)](https://javadoc.io/doc/org.fugerit.java/fj-core)

Simple utilities for IO, Configuration, DB, Language types and more.

## module : [fj-core-jvfs](fj-core-jvfs/README.md)

[![Maven Central](https://img.shields.io/maven-central/v/org.fugerit.java/fj-core-jvfs.svg)](https://mvnrepository.com/artifact/org.fugerit.java/fj-core-jvfs) 
[![javadoc](https://javadoc.io/badge2/org.fugerit.java/fj-core-jvfs/javadoc.svg)](https://javadoc.io/doc/org.fugerit.java/fj-core-jvfs)

Abstraction layer for a virtual file system (can be based on real file syste, database, class loader etc.)

## module : [fj-tool](fj-tool/README.md)

[![Maven Central](https://img.shields.io/maven-central/v/org.fugerit.java/fj-tool.svg)](https://mvnrepository.com/artifact/org.fugerit.java/fj-tool) 
[![javadoc](https://javadoc.io/badge2/org.fugerit.java/fj-tool/javadoc.svg)](https://javadoc.io/doc/org.fugerit.java/fj-tool)

Sample tool wrappers for some of [fj-core](../fj-core/README.md) module functionalities.

