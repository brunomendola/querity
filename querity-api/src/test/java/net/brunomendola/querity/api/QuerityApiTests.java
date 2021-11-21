package net.brunomendola.querity.api;

import net.brunomendola.querity.api.domain.Person;
import org.junit.jupiter.api.Test;

import java.util.List;

import static net.brunomendola.querity.api.Operator.*;
import static net.brunomendola.querity.api.Querity.*;
import static net.brunomendola.querity.api.Sort.Direction.DESC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QuerityApiTests {

  private final Querity querity = new QuerityDummyImpl();

  @Test
  void givenEmptyQuery_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Querity.query()
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithOneEqualsCondition_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Querity.query()
            .filter(filterBy("lastName", EQUALS, "Skywalker"))
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithOneNotEqualsCondition_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Querity.query()
            .filter(filterBy("lastName", NOT_EQUALS, "Skywalker"))
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithOneStartsWithCondition_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Querity.query()
            .filter(filterBy("lastName", STARTS_WITH, "Sky"))
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithOneEndsWithCondition_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Querity.query()
            .filter(filterBy("lastName", ENDS_WITH, "walker"))
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithOneContainsCondition_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Querity.query()
            .filter(filterBy("lastName", CONTAINS, "walk"))
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithOneGreaterThanCondition_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Querity.query()
            .filter(filterBy("height", GREATER_THAN, "1.72"))
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithOneGreaterThanEqualsCondition_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Querity.query()
            .filter(filterBy("height", GREATER_THAN_EQUALS, "1.72"))
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithOneLesserThanCondition_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Querity.query()
            .filter(filterBy("height", LESSER_THAN, "1.72"))
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithOneLesserThanEqualsCondition_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Querity.query()
            .filter(filterBy("height", LESSER_THAN_EQUALS, "1.72"))
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithOneIsNullCondition_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Querity.query()
            .filter(filterBy("lastName", IS_NULL))
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithOneIsNotNullCondition_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Querity.query()
            .filter(filterBy("lastName", IS_NOT_NULL))
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithEqualsConditionAndWithoutValue_whenBuild_thenThrowIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> filterBy("lastName", EQUALS),
        "The operator EQUALS requires 1 value(s)");
  }

  @Test
  void givenFilterWithIsNullConditionAndValue_whenBuild_thenThrowIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> filterBy("lastName", IS_NULL, "value"),
        "The operator IS_NULL requires 0 value(s)");
  }

  @Test
  void givenFilterWithNotConditionContainingSimpleCondition_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Querity.query()
            .filter(not(filterBy("lastName", EQUALS, "Skywalker")))
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithNotConditionContainingConditionsWrapper_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Querity.query()
            .filter(
                not(or(
                    filterBy("firstName", EQUALS, "Luke"),
                    filterBy("lastName", EQUALS, "Skywalker")
                ))
            ).build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithTwoEqualsConditions_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Querity.query()
            .filter(and(
                filterBy("firstName", EQUALS, "Luke"),
                filterBy("lastName", EQUALS, "Skywalker")
            ))
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithTwoEqualsConditionsWithOrLogic_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Querity.query()
            .filter(or(
                filterBy("firstName", EQUALS, "Luke"),
                filterBy("lastName", EQUALS, "Skywalker")
            ))
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithNestedConditions_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Querity.query()
            .filter(and(
                filterBy("lastName", EQUALS, "Skywalker"),
                or(
                    filterBy("firstName", EQUALS, "Anakin"),
                    filterBy("firstName", EQUALS, "Luke")
                ))
            )
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenPagination_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Querity.query()
            .pagination(1, 5)
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenSort_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Querity.query()
            .sort(sortBy("lastName"), sortBy("firstName", DESC))
            .build());
    assertThat(people).isNotNull();
  }
}
