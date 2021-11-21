package net.brunomendola.querity.common.util;

import lombok.RequiredArgsConstructor;
import net.brunomendola.querity.api.Condition;
import net.brunomendola.querity.api.ConditionsWrapper;
import net.brunomendola.querity.api.NotCondition;
import net.brunomendola.querity.api.SimpleCondition;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static net.brunomendola.querity.api.Operator.EQUALS;
import static net.brunomendola.querity.api.Querity.*;
import static org.assertj.core.api.Assertions.assertThat;

class ConditionUtilsTests {

  public static final HashSet<Class<?>> IMPLEMENTATION_CLASSES = new HashSet<>(Arrays.asList(
      MySimpleCondition.class, MyConditionWrapper.class, MyNotCondition.class));

  @Test
  void givenSimpleCondition_whenGetConditionImplementation_thenReturnInstanceOfMySimpleCondition() {
    Condition condition = getSimpleCondition();
    assertThat(ConditionUtils.getConditionImplementation(IMPLEMENTATION_CLASSES,
        condition)).containsInstanceOf(MySimpleCondition.class);
  }

  @Test
  void givenConditionsWrapper_whenGetConditionImplementation_thenReturnInstanceOfMyConditionsWrapper() {
    Condition condition = and(getSimpleCondition());
    assertThat(ConditionUtils.getConditionImplementation(IMPLEMENTATION_CLASSES,
        condition)).containsInstanceOf(MyConditionWrapper.class);
  }

  @Test
  void givenSimpleCondition_whenGetConditionImplementation_thenReturnInstanceOfMyNotCondition() {
    Condition condition = not(getSimpleCondition());
    assertThat(ConditionUtils.getConditionImplementation(IMPLEMENTATION_CLASSES,
        condition)).containsInstanceOf(MyNotCondition.class);
  }

  private static SimpleCondition getSimpleCondition() {
    return filterBy("fieldName", EQUALS, "value");
  }

  @RequiredArgsConstructor
  static class MySimpleCondition {
    private final SimpleCondition condition;
  }

  @RequiredArgsConstructor
  static class MyConditionWrapper {
    private final ConditionsWrapper condition;
  }

  @RequiredArgsConstructor
  static class MyNotCondition {
    private final NotCondition condition;
  }
}
