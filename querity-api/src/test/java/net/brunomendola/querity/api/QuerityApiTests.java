package net.brunomendola.querity.api;

import net.brunomendola.querity.api.domain.Person;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QuerityApiTests {

  private final Querity querity = new QuerityDummyImpl();

  @Test
  void givenEmptyQuery_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Query.builder()
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithOneEqualsCondition_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Query.builder()
            .filter(SimpleCondition.builder().propertyName("lastName").operator(Operator.EQUALS).value("Skywalker").build())
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithOneNotEqualsCondition_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Query.builder()
            .filter(SimpleCondition.builder().propertyName("lastName").operator(Operator.NOT_EQUALS).value("Skywalker").build())
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithOneStartsWithCondition_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Query.builder()
            .filter(SimpleCondition.builder().propertyName("lastName").operator(Operator.STARTS_WITH).value("Sky").build())
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithOneEndsWithCondition_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Query.builder()
            .filter(SimpleCondition.builder().propertyName("lastName").operator(Operator.ENDS_WITH).value("walker").build())
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithOneContainsCondition_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Query.builder()
            .filter(SimpleCondition.builder().propertyName("lastName").operator(Operator.CONTAINS).value("walk").build())
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithOneGreaterThanCondition_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Query.builder()
            .filter(SimpleCondition.builder().propertyName("height").operator(Operator.GREATER_THAN).value("1.72").build())
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithOneGreaterThanEqualsCondition_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Query.builder()
            .filter(SimpleCondition.builder().propertyName("height").operator(Operator.GREATER_THAN_EQUALS).value("1.72").build())
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithOneLesserThanCondition_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Query.builder()
            .filter(SimpleCondition.builder().propertyName("height").operator(Operator.LESSER_THAN).value("1.72").build())
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithOneLesserThanEqualsCondition_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Query.builder()
            .filter(SimpleCondition.builder().propertyName("height").operator(Operator.LESSER_THAN_EQUALS).value("1.72").build())
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithOneIsNullCondition_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Query.builder()
            .filter(SimpleCondition.builder().propertyName("lastName").operator(Operator.IS_NULL).build())
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithOneIsNotNullCondition_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Query.builder()
            .filter(SimpleCondition.builder().propertyName("lastName").operator(Operator.IS_NOT_NULL).build())
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithEqualsConditionAndWithoutValue_whenBuild_thenThrowIllegalArgumentException() {
    SimpleCondition.SimpleConditionBuilder builder = SimpleCondition.builder().propertyName("lastName").operator(Operator.EQUALS);
    assertThrows(IllegalArgumentException.class, builder::build,
        "The operator EQUALS requires 1 value(s)");
  }

  @Test
  void givenFilterWithIsNullConditionAndValue_whenBuild_thenThrowIllegalArgumentException() {
    SimpleCondition.SimpleConditionBuilder builder = SimpleCondition.builder().propertyName("lastName").operator(Operator.IS_NULL).value("value");
    assertThrows(IllegalArgumentException.class, builder::build,
        "The operator IS_NULL requires 0 value(s)");
  }

  @Test
  void givenFilterWithNotConditionContainingSimpleCondition_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Query.builder()
            .filter(NotCondition.builder()
                .condition(SimpleCondition.builder().propertyName("lastName").operator(Operator.EQUALS).value("Skywalker").build())
                .build())
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithNotConditionContainingConditionsWrapper_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Query.builder()
            .filter(
                NotCondition.builder()
                    .condition(ConditionsWrapper.builder()
                        .logic(LogicOperator.OR)
                        .conditions(Arrays.asList(
                            SimpleCondition.builder().propertyName("firstName").operator(Operator.EQUALS).value("Luke").build(),
                            SimpleCondition.builder().propertyName("lastName").operator(Operator.EQUALS).value("Skywalker").build()
                        ))
                        .build())
                    .build()
            ).build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithTwoEqualsConditions_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Query.builder()
            .filter(ConditionsWrapper.builder()
                .conditions(Arrays.asList(
                    SimpleCondition.builder().propertyName("firstName").operator(Operator.EQUALS).value("Luke").build(),
                    SimpleCondition.builder().propertyName("lastName").operator(Operator.EQUALS).value("Skywalker").build()
                ))
                .build())
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithTwoEqualsConditionsWithOrLogic_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Query.builder()
            .filter(ConditionsWrapper.builder()
                .logic(LogicOperator.OR)
                .conditions(Arrays.asList(
                    SimpleCondition.builder().propertyName("firstName").operator(Operator.EQUALS).value("Luke").build(),
                    SimpleCondition.builder().propertyName("lastName").operator(Operator.EQUALS).value("Skywalker").build()
                ))
                .build())
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithNestedConditions_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Query.builder()
            .filter(ConditionsWrapper.builder()
                .conditions(Arrays.asList(
                    SimpleCondition.builder().propertyName("lastName").operator(Operator.EQUALS).value("Skywalker").build(),
                    ConditionsWrapper.builder()
                        .logic(LogicOperator.OR)
                        .conditions(Arrays.asList(
                            SimpleCondition.builder().propertyName("firstName").operator(Operator.EQUALS).value("Anakin").build(),
                            SimpleCondition.builder().propertyName("firstName").operator(Operator.EQUALS).value("Luke").build()
                        ))
                        .build()
                ))
                .build())
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenPagination_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Query.builder()
            .pagination(Pagination.builder().page(1).pageSize(5).build())
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenSort_whenFindAll_thenReturnListOfEntity() {
    List<Person> people = querity.findAll(Person.class,
        Query.builder()
            .sort(Arrays.asList(
                Sort.builder().propertyName("lastName").build(),
                Sort.builder().propertyName("firstName").direction(Sort.Direction.DESC).build()))
            .build());
    assertThat(people).isNotNull();
  }
}
