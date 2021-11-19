package net.brunomendola.querity.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import net.brunomendola.querity.api.Condition;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConditionUtils {
  public static <T> Set<Class<? extends T>> findSubclasses(Class<T> baseClass) {
    return new Reflections(baseClass.getPackage().getName())
        .getSubTypesOf(baseClass);
  }

  public static <T> Optional<T> getConditionImplementation(Set<Class<? extends T>> implementationClasses, Condition condition) {
    return findClassWithConstructorArgumentOfType(implementationClasses, condition.getClass())
        .map(clazz -> constructInstanceWithArgument(clazz, condition));
  }

  public static <T, A> Optional<Class<? extends T>> findClassWithConstructorArgumentOfType(Set<Class<? extends T>> allClasses,
                                                                                           Class<? extends A> constructorArgumentType) {
    return allClasses.stream()
        .filter(clazz -> Arrays.stream(clazz.getDeclaredConstructors())
            .anyMatch(constructor -> constructor.getParameterCount() == 1 &&
                constructor.getParameterTypes()[0].equals(constructorArgumentType)))
        .findAny();
  }

  @SneakyThrows
  @SuppressWarnings("java:S3011") // we want to have this generic constructor but also package-private classes
  private static <T, A> T constructInstanceWithArgument(Class<T> clazz, A argument) {
    Constructor<T> constructor = clazz.getDeclaredConstructor(argument.getClass());
    constructor.setAccessible(true);
    return constructor.newInstance(argument);
  }
}
