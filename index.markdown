---
layout: default
---

Querity is a generic interface to run database queries in your Java application.

It supports various database technologies, both **SQL** and **NoSQL**, and each support is built into modules, so you
can import the one which fits into your project.

Why you should use Querity?

✔ learn once, use everywhere

✔ zero logic needed apart building a generic Query object

✔ switch database without rewriting the logic of your application

✔ ready to use REST API which implements filtering, sorting and pagination, to be consumed by your UI components

✔ expose the same REST API in all your projects

# Requirements

* Java 8+
* Spring Framework (optionally Spring Boot... makes things a lot simpler)

# Installing

All releases are published to the Maven Central repository
(see [here](https://search.maven.org/search?q=net.brunomendola.querity)).

Install one of the modules in your project as follows (see [Available modules](#available-modules)).

Maven:

```xml

<dependency>
  <groupId>net.brunomendola.querity</groupId>
  <artifactId>querity-spring-data-jpa</artifactId>
  <version>{{ site.querity_version }}</version>
</dependency>
```

Gradle:

```groovy
implementation "net.brunomendola.querity:querity-spring-data-jpa:{{ site.querity_version }}"
```

See [Releases](https://github.com/brunomendola/querity/releases) to check the latest version and see the changelogs.

All modules are "Spring Boot starters", if you use Spring Boot you just need to add the dependency to your project and
start using it, no other configuration needed.

# Available modules

Currently, Querity supports the following technologies with its modules:

## querity-spring-data-jpa

Supports [Spring Data JPA](https://spring.io/projects/spring-data-jpa).

## querity-spring-data-mongodb

Supports [Spring Data MongoDB](https://spring.io/projects/spring-data-mongodb).

## querity-spring-web

Supports JSON serialization and deserialization of Querity objects
in [Spring Web MVC](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html).

# Quick start

In your Spring Boot project, add the dependency as shown in [Installing](#installing) and create a Spring service as
follows:

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

# Features

Use the static method `Querity.query().build()` to build an empty query (see the following chapters to build a more
complex query).

You can `@Autowire` the `Querity` service to run queries against the database configured in your application.

Having the `Querity` service, you can use the following instance methods:

* `Querity.findAll(entityClass, query)` to run the query and retrieve the results;
* `Querity.count(entityClass, condition)` to just count the elements filtered by the condition.

## Filters

Use `Querity.query().filter(condition).build()` to build a query with filters.

You can build filters which contains **conditions**, and they can be simple or nested conditions.

### Conditions

#### Simple conditions

Use `Querity.filterBy` to build a simple condition with a property name, an operator and a value (if needed by the
operator, e.g. IS_NULL does not need a value).

```
Query query = Querity.query()
    .filter(filterBy("lastName", EQUALS, "Skywalker"))
    .build();
```

##### Operators

* EQUALS
* NOT_EQUALS
* STARTS_WITH
* ENDS_WITH
* CONTAINS
* GREATER_THAN
* GREATER_THAN_EQUALS
* LESSER_THAN
* LESSER_THAN_EQUALS
* IS_NULL
* IS_NOT_NULL

#### AND conditions

Use `Querity.and` and add more conditions to wrap them in a logical AND.

```
Query query = Querity.query()
    .filter(
        and(
            filterBy("firstName", EQUALS, "Luke"),
            filterBy("lastName", EQUALS, "Skywalker")
        )
    ).build();
```

You can also nest more levels of complex conditions.

#### OR conditions

Use `Querity.or` and add more conditions to wrap them in a logical OR.

```
Query query = Querity.query()
    .filter(
        or(
            filterBy("lastName", EQUALS, "Skywalker"),
            filterBy("lastName", EQUALS, "Kenobi")
        )
    ).build();
```

You can also nest more levels of complex conditions.

#### NOT conditions

Use `Querity.not` and specify a condition to wrap it in a logical NOT.

You can wrap simple conditions:

```
Query query = Querity.query()
    .filter(not(filterBy("lastName", EQUALS, "Skywalker")))
    .build();
```

or complex conditions:

```
Query query = Querity.query()
    .filter(
        not(and(
            filterBy("firstName", EQUALS, "Luke"),
            filterBy("lastName", EQUALS, "Skywalker")
        ))
    ).build();
```

#### Native conditions

Use `Querity.filterByNative` and specify a native condition to use a database-specific condition in your query.

This could be useful if you really want to add very complex query conditions that cannot be built with the Querity APIs.

> Native conditions are supported only with Java API, not REST.

Example with Spring Data JPA:

```
Specification<Person> specification = (root, cq, cb) -> cb.equal(root.get("lastName"), "Skywalker");
Query query = Querity.query()
    .filter(filterByNative(specification))
    .build();
```

Example with Spring Data MongoDB:

```
Criteria criteria = Criteria.where("lastName").is("Skywalker");
Query query = Querity.query()
    .filter(filterByNative(criteria))
    .build();
```


## Sorting

Use `Querity.query().sort(...).build()` to build a query with sorting.

Use `Querity.sortBy` to build a sort criteria.

```
Query query = Querity.query()
    .sort(sortBy("lastName"), sortBy("birthDate", DESC))
    .build();
```

## Pagination

Use `Querity.query().pagination(page, pageSize).build()` to build a query with pagination.

```
Query query = Querity.query()
    .pagination(1, 5)
    .build();
```

# Support for Spring MVC and REST APIs

Querity objects need some configuration to be correctly deserialized when they are received by a
Spring `@RestController` as a JSON payload.

These configurations are automatically done by importing the `querity-spring-web` module (see [Installing](#installing))
.

After that, you'll be able to use a `Query` or `Condition` as a controller parameter and build REST APIs like this:

```java
import net.brunomendola.querity.api.Query;

@RestController
public class MyRestController {

  @Autowired
  MyService service;

  @GetMapping(value = "/people", produces = MediaType.APPLICATION_JSON_VALUE)
  Result<Person> getPeople(@RequestParam(required = false) Query q) {
    return service.getPeople(q);
  }
}
```

Then the above REST API could be invoked like this:

```bash
curl 'http://localhost:8080/people?q={"filter":{"and":[{"propertyName":"lastName","operator":"EQUALS","value":"Skywalker"},{"propertyName":"lastName","operator":"EQUALS","value":"Luke"}]}}'
```

## Support for DTO layer

You may not want to expose database entities to the clients of your REST APIs.

If you implemented a DTO layer, with DTO objects mapping properties from your entities, the queries which would come to your REST APIs will have the DTO property names, not the entity ones. This is a problem, because some queries would not work, looking for non-existing properties on the entities.

> Note: it would work without any help only if properties in the DTO have the same name and structure of the properties in the entity

Querity has a "preprocessing layer" which you can use to map the DTO property names in your Query to the entity property names.

### Preprocessors

Use the `@WithPreprocessor("beanName")` annotation to annotate `Query` or `Condition` parameters in your Spring RestControllers.

The `beanName` argument must refer to an existing Spring bean which implements `QueryPreprocessor`.

When entering your controller method, the `Query` or `Condition` object would already be preprocessed.

#### PropertyNameMappingPreprocessor

`PropertyNameMappingPreprocessor` is a `QueryProcessor` abstraction to convert property names.

Querity already has a simple mapper, `SimplePropertyNameMapper`, which simply does a property name conversion by looking into a Map you must provide.

To use `PropertyNameMappingPreprocessor` with `SimplePropertyNameMapper`, instantiate a bean into your Spring configuration:

```java
@SpringBootApplication
public class MyApplication {
  public static void main(String[] args) {
    SpringApplication.run(MyApplication.class, args);
  }

  @Bean
  public QueryPreprocessor preprocessor1() {
    return new PropertyNameMappingPreprocessor(
        SimplePropertyNameMapper.builder()
            .mapping("prop1", "prop2") // customize your mappings here
            .build());
  }
}
```

and use it to annotate the parameter in your RestController.

```java
@RestController
public class MyRestController {

  @Autowired
  MyService service;

  @GetMapping(value = "/people", produces = MediaType.APPLICATION_JSON_VALUE)
  Result<Person> getPeople(@RequestParam(required = false) @WithPreprocessor("preprocessor1") Query q) {
    return service.getPeople(q);
  }
}
```
