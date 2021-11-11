package net.brunomendola.querity.api;

import net.brunomendola.querity.api.domain.Person;
import org.junit.jupiter.api.Test;

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
            .addFilterCondition("lastName", Operator.EQUALS, "Skywalker")
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithTwoEqualsConditions_whenFindAll_thenReturnListOfEntity() {
    Querity querity = new QuerityDummyImpl();
    List<Person> people = querity.findAll(Person.class,
        Query.builder()
            .addFilterCondition("firstName", Operator.EQUALS, "Luke")
            .addFilterCondition("lastName", Operator.EQUALS, "Skywalker")
            .build());
    assertThat(people).isNotNull();
  }

  @Test
  void givenFilterWithTwoEqualsConditionsWithOrLogic_whenFindAll_thenReturnListOfEntity() {
    Querity querity = new QuerityDummyImpl();
    List<Person> people = querity.findAll(Person.class,
        Query.builder()
            .setFilterLogic(LogicOperator.OR)
            .addFilterCondition("firstName", Operator.EQUALS, "Luke")
            .addFilterCondition("lastName", Operator.EQUALS, "Skywalker")
            .build());
    assertThat(people).isNotNull();
  }
}
