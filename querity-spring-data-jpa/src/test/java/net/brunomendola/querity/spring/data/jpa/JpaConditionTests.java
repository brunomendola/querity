package net.brunomendola.querity.spring.data.jpa;

import net.brunomendola.querity.api.Condition;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static net.brunomendola.querity.common.util.ConditionUtils.findClassWithConstructorArgumentOfType;
import static net.brunomendola.querity.common.util.ConditionUtils.findSubclasses;
import static org.assertj.core.api.Assertions.assertThat;

class JpaConditionTests {
  @Test
  void testAllConditionClassesImplemented() {
    Set<Class<? extends Condition>> conditionClasses = findSubclasses(Condition.class);

    Set<Class<? extends JpaCondition>> implementationClasses = findSubclasses(JpaCondition.class);

    assertThat(conditionClasses)
        .map(clazz -> findClassWithConstructorArgumentOfType(implementationClasses, clazz))
        .allMatch(Optional::isPresent);
  }
}
