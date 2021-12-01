<img alt="Querity-logo" src="https://user-images.githubusercontent.com/1853562/142502086-2a352854-2315-4fe5-b1a3-d7730a47fe36.jpeg" width="80" height="80"/> Querity
=======

[![Build](https://github.com/brunomendola/querity/actions/workflows/maven.yml/badge.svg)](https://github.com/brunomendola/querity/actions/workflows/maven.yml)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=brunomendola_querity&metric=bugs)](https://sonarcloud.io/summary/new_code?id=brunomendola_querity)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=brunomendola_querity&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=brunomendola_querity)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=brunomendola_querity&metric=coverage)](https://sonarcloud.io/summary/new_code?id=brunomendola_querity)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=brunomendola_querity&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=brunomendola_querity)

Generic interface to query a database.

## Description

The aim of this project is to provide a simple generic interface, supporting fluent Java and REST APIs, to query
databases in Java applications.

The modules implement the support for different databases and frameworks.

Why you should use Querity?

✔ learn once, use everywhere

✔ zero logic needed apart building a generic Query object

✔ switch database without rewriting the logic of your application

✔ ready to use REST API which implements filtering, sorting and pagination, to be consumed by your UI components

✔ expose the same REST API in all your projects

## Getting Started

### Dependencies

* Java 8+

### Installing

All releases are published to the Maven Central repository (
see [here](https://search.maven.org/search?q=net.brunomendola.querity)).

Available modules:

* **querity-spring-data-jpa**: supports Spring Data JPA
* **querity-spring-data-mongodb**: supports Spring Data MongoDB
* **querity-spring-web**: supports JSON serialization and deserialization of Querity objects in Spring Web MVC

All modules are "Spring Boot starters", you just need to add the dependency to your Spring Boot project and start using
it, no other configuration needed.

Maven:

```xml
<dependency>
  <groupId>net.brunomendola.querity</groupId>
  <artifactId>querity-spring-data-jpa</artifactId>
  <version>${querity.version}</version>
</dependency>
```

Gradle:

```groovy
implementation "net.brunomendola.querity:querity-spring-data-jpa:${querityVersion}"
```

### Usage

```java
import static net.brunomendola.querity.api.Querity.*;
import static net.brunomendola.querity.api.Operator.*;
import static net.brunomendola.querity.api.Sort.Direction.*;

@Service
public class MyService {

  @Autowired
  Querity querity;

  public Result<Person> getPeople() {
    Query query = Querity.query()
        // customize filters, pagination, sorting...
        .filter(
            not(and(
                filterBy("lastName", EQUALS, "Skywalker"),
                filterBy("firstName", EQUALS, "Luke")
            ))
        )
        .sort(sortBy("lastName"), sortBy("birthDate", DESC))
        .pagination(1, 10)
        .build();
    List<Person> items = querity.findAll(Person.class, query);
    Long totalCount = querity.count(Person.class, query.getFilter());
    return new Result<>(items, totalCount);
  }

  record Result<T>(List<T> items, Long totalCount) {
  }
}
```

In the above example, the `findAll` method returns the first of n pages with max 10 elements of all people NOT named
Luke Skywalker, sorted by last name and then birthdate descending.<br />
The `count` method returns the total filtered items count excluding pagination (the record keyword is implemented from
Java 14).

> Note the static imports to improve the readability.

## Documentation

Read the full documentation [here](https://brunomendola.github.io/querity).

## Access to SNAPSHOT builds

Commits to the `main` branch are automatically built and deployed to OSSRH SNAPSHOTs Maven repository.

To use the SNAPSHOTs in your project, add the SNAPSHOTs repository as follows.

> Of course using SNAPSHOTs is not recommended, but if you feel brave you can do it to test new not-yet-released features.

Maven:

```xml
<repositories>
    <repository>
      <id>ossrh-snapshots-repo</id>
      <url>https://s01.oss.sonatype.org/content/repositories/snapshots</url>
      <releases><enabled>false</enabled></releases>
      <snapshots><enabled>true</enabled></snapshots>
    </repository>
</repositories>
```

Gradle:

```groovy
repositories {
  maven {
    url "https://s01.oss.sonatype.org/content/repositories/snapshots"
    mavenContent { snapshotsOnly() }
  }
}
```

Browse the
repository [here](https://s01.oss.sonatype.org/content/repositories/snapshots/net/brunomendola/querity/querity-parent/)
to find the latest SNAPSHOT version.

## Development

### Running tests

Run with Maven (wrapper):

```bash
./mvnw test
```

or just run them with your favourite IDE.

### Test dataset

The test dataset is generated with [Mockaroo](https://mockaroo.com).

If you want to make changes, you don't need to do it manually, please find the
schema [here](https://mockaroo.com/ec155390).

## Authors

Contributors names and contact info

* Bruno Mendola [@brunomendola](https://twitter.com/brunomendola)

**PRs are welcome!**

## Version History

See [Releases](https://github.com/brunomendola/querity/releases).

## License

This project is licensed under the Apache 2.0 License - see the [LICENSE](LICENSE) file for details
