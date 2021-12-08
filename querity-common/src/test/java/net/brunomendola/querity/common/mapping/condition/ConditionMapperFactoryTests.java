package net.brunomendola.querity.common.mapping.condition;

import net.brunomendola.querity.api.Condition;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ConditionMapperFactoryTests {
  @Test
  void givenNotSupportedCondition_whenGetConditionMapper_thenThrowsIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class,
        () -> ConditionMapperFactory.getConditionMapper(new Condition() {
        }),
        "Condition mapper not found");
  }
}
