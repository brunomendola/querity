package net.brunomendola.querity.spring.data.mongodb;

import net.brunomendola.querity.api.Condition;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static net.brunomendola.querity.common.util.ConditionUtils.findClassWithConstructorArgumentOfType;
import static net.brunomendola.querity.common.util.ConditionUtils.findSubclasses;
import static org.assertj.core.api.Assertions.assertThat;

class MongodbConditionTests {
  @Test
  void testAllConditionClassesImplemented() {
    Set<Class<? extends Condition>> conditionClasses = findSubclasses(Condition.class);

    Set<Class<? extends MongodbCondition>> implementationClasses = findSubclasses(MongodbCondition.class);

    assertThat(conditionClasses)
        .map(clazz -> findClassWithConstructorArgumentOfType(implementationClasses, clazz))
        .allMatch(Optional::isPresent);
  }
}
