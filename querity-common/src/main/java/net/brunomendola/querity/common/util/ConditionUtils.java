package net.brunomendola.querity.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.brunomendola.querity.api.Condition;

import java.util.Optional;
import java.util.Set;

import static net.brunomendola.querity.common.util.ReflectionUtils.constructInstanceWithArgument;
import static net.brunomendola.querity.common.util.ReflectionUtils.findClassWithConstructorArgumentOfType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConditionUtils {

  public static <T> Optional<T> getConditionImplementation(Set<Class<? extends T>> implementationClasses, Condition condition) {
    return findClassWithConstructorArgumentOfType(implementationClasses, condition.getClass())
        .map(clazz -> constructInstanceWithArgument(clazz, condition));
  }
}
