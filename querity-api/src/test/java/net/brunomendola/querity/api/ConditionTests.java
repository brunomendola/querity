package net.brunomendola.querity.api;

import org.junit.jupiter.api.Test;

import static net.brunomendola.querity.api.Operator.EQUALS;
import static net.brunomendola.querity.api.Querity.*;
import static org.assertj.core.api.Assertions.assertThat;

class ConditionTests {
  @Test
  void givenSimpleCondition_whenIsEmpty_thenReturnFalse() {
    Condition condition = getSimpleCondition();
    assertThat(condition.isEmpty()).isFalse();
  }

  @Test
  void givenNotEmptyConditionsWrapper_whenIsEmpty_thenReturnFalse() {
    Condition condition = and(getSimpleCondition());
    assertThat(condition.isEmpty()).isFalse();
  }

  @Test
  void givenEmptyConditionsWrapper_whenIsEmpty_thenReturnTrue() {
    Condition condition = and();
    assertThat(condition.isEmpty()).isTrue();
  }

  @Test
  void givenNotConditionWithSimpleCondition_whenIsEmpty_thenReturnFalse() {
    Condition condition = not(getSimpleCondition());
    assertThat(condition.isEmpty()).isFalse();
  }

  @Test
  void givenNotConditionWithNotEmptyConditionsWrapper_whenIsEmpty_thenReturnFalse() {
    Condition condition = not(and(getSimpleCondition()));
    assertThat(condition.isEmpty()).isFalse();
  }

  @Test
  void givenNotConditionWithEmptyConditionsWrapper_whenIsEmpty_thenReturnTrue() {
    Condition condition = not(and());
    assertThat(condition.isEmpty()).isTrue();
  }

  private static SimpleCondition getSimpleCondition() {
    return filterBy("lastName", EQUALS, "Skywalker");
  }

}
