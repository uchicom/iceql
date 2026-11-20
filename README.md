orz
===

> **Fork notice**: orz is a fork of [gitblit/iciql](https://github.com/gitblit/iciql) **2.2.1** (Apache License 2.0), originally derived from H2 JaQu.
> Versioning restarts at 0.0.1 and is independent of upstream iciql versions.
> The upstream PRs were left unattended, so this project restarts as `com.uchicom.orz`.
> The Java package is now `com.uchicom.orz` and the Maven artifact is `com.uchicom:orz`.
> See [CHANGELOG.md](CHANGELOG.md) and [NOTICE](NOTICE) for the modifications.

[![Maven Central](https://img.shields.io/maven-central/v/com.uchicom/orz.svg)](https://search.maven.org/artifact/com.uchicom/orz)
[![Maven Central](https://img.shields.io/github/license/uchicom/orz.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)

orz **is**...

- a model-based, database access wrapper for JDBC
- for modest database schemas and basic statement generation
- for those who want to write code, instead of SQL, using IDE completion and compile-time type-safety
- small (<250KB with debug symbols) with no runtime dependencies
- pronounced *icicle* (although it could be French: *ici ql* - here query language)
- a friendly fork of the H2 [JaQu](http://h2database.com/html/jaqu.html) project

orz **is not**...

- a complete alternative to JDBC
- designed to compete with more powerful database query tools like [jOOQ](http://jooq.sourceforge.net) or [QueryDSL](http://source.mysema.com/display/querydsl/Querydsl)
- designed to compete with enterprise ORM tools like [Hibernate](http://www.hibernate.org) or [mybatis](http://www.mybatis.org)

Supported Databases (Unit-Tested)
-------
- [H2](http://h2database.com) 1.4
- [HSQLDB](http://hsqldb.org) 2.3
- [Derby](http://db.apache.org/derby) 10.12
- [MySQL](http://mysql.com) 5.6
- [PostgreSQL](http://postgresql.org) 9.5
- [SQLite](http://www.sqlite.org) 3.8

Support for others is possible and may only require creating a simple "dialect" class.

Downloading
-----------

orz is distributed through Maven Central (since 0.0.1).

```xml
<dependencies>
    <dependency>
        <groupId>com.uchicom</groupId>
        <artifactId>orz</artifactId>
        <version>0.0.1</version>
    </dependency>
</dependencies>
```


License
-------
orz is distributed under the terms of the [Apache Software Foundation license, version 2.0](http://www.apache.org/licenses/LICENSE-2.0).<br/>
The text of the license is included in the file LICENSE in the root of the project.

Java Runtime Requirement
-------
orz requires a Java 8 Runtime Environment (JRE) or a Java 8 Development Kit (JDK).
 
Getting help
-------
Read the online documentation of the upstream project at the [iciql website](http://iciql.com)<br/>
Issues & source code @ [GitHub](https://github.com/uchicom/orz)

Building orz
----------------

You may use Maven to build the project:

    mvn clean package
    
You may use Maven to run the test suite on the default database:

    mvn clean test

You may use Maven to run the test suite on all tested databases:

    mvn clean test-compile exec:exec
