package net.brunomendola.querity.common.util;

import lombok.RequiredArgsConstructor;
import net.brunomendola.querity.api.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static net.brunomendola.querity.api.Operator.EQUALS;
import static net.brunomendola.querity.api.Querity.*;
import static org.assertj.core.api.Assertions.assertThat;

class ConditionUtilsTests {

  public static final HashSet<Class<?>> IMPLEMENTATION_CLASSES = new HashSet<>(Arrays.asList(
      MySimpleCondition.class, MyAndConditionWrapper.class, MyOrConditionWrapper.class, MyNotCondition.class));

  @Test
  void givenSimpleCondition_whenGetConditionImplementation_thenReturnInstanceOfMySimpleCondition() {
    Condition condition = getSimpleCondition();
    assertThat(ConditionUtils.getConditionImplementation(IMPLEMENTATION_CLASSES,
        condition)).containsInstanceOf(MySimpleCondition.class);
  }

  @Test
  void givenAndConditionsWrapper_whenGetConditionImplementation_thenReturnInstanceOfMyConditionsWrapper() {
    Condition condition = and(getSimpleCondition());
    assertThat(ConditionUtils.getConditionImplementation(IMPLEMENTATION_CLASSES,
        condition)).containsInstanceOf(MyAndConditionWrapper.class);
  }

  @Test
  void givenOrConditionsWrapper_whenGetConditionImplementation_thenReturnInstanceOfMyConditionsWrapper() {
    Condition condition = or(getSimpleCondition());
    assertThat(ConditionUtils.getConditionImplementation(IMPLEMENTATION_CLASSES,
        condition)).containsInstanceOf(MyOrConditionWrapper.class);
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
  static class MyAndConditionWrapper {
    private final AndConditionsWrapper condition;
  }

  @RequiredArgsConstructor
  static class MyOrConditionWrapper {
    private final OrConditionsWrapper condition;
  }

  @RequiredArgsConstructor
  static class MyNotCondition {
    private final NotCondition condition;
  }
}
