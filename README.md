<img alt="Querity-logo" src="https://user-images.githubusercontent.com/1853562/142502086-2a352854-2315-4fe5-b1a3-d7730a47fe36.jpeg" width="80" height="80"/> Querity
=======

![Build](https://github.com/brunomendola/querity/actions/workflows/maven.yml/badge.svg)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=brunomendola_querity&metric=bugs)](https://sonarcloud.io/summary/new_code?id=brunomendola_querity)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=brunomendola_querity&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=brunomendola_querity)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=brunomendola_querity&metric=coverage)](https://sonarcloud.io/summary/new_code?id=brunomendola_querity)

Generic interface to query a database.

## Description

The aim of this project is to provide a simple fluent generic interface to query databases in Java applications.

The modules implement the support for different databases and frameworks.

## Getting Started

### Dependencies

* Java 8+

### Installing

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
implementation "net.brunomendola.querity:querity-spring-data-jpa:${querity.version}"
```

### Usage

```
@Autowired
Querity querity;

public List<Person> getPeople() {
    Query query = Query.builder()
        // add filters, sorting, etc.
        .build();
    return querity.findAll(Person.class, query);
}
```

## Authors

Contributors names and contact info

* Bruno Mendola [@brunomendola](https://twitter.com/brunomendola)

## Version History

* No release yet

## License

This project is licensed under the Apache 2.0 License - see the LICENSE file for details
