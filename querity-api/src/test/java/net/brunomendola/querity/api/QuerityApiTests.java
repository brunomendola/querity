package net.brunomendola.querity.api;

import net.brunomendola.querity.api.domain.Person;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class QuerityApiTests {
  @Test
  void givenEmptyQuery_whenFindAll_thenReturnListOfEntity() {
    Querity querity = new QuerityDummyImpl();
    List<Person> people = querity.findAll(Person.class,
        Query.builder()
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithOneEqualsCondition_whenFindAll_thenReturnListOfEntity() {
    Querity querity = new QuerityDummyImpl();
    List<Person> people = querity.findAll(Person.class,
        Query.builder()
            .filter(SimpleCondition.builder().propertyName("lastName").operator(Operator.EQUALS).value("Skywalker").build())
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithTwoEqualsConditions_whenFindAll_thenReturnListOfEntity() {
    Querity querity = new QuerityDummyImpl();
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
    Querity querity = new QuerityDummyImpl();
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
    Querity querity = new QuerityDummyImpl();
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
}
