package net.brunomendola.querity.spring.data.jpa;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.query.criteria.internal.path.PluralAttributePath;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class JpaPropertyUtils {
  @SuppressWarnings("java:S1452") // we don't really know what type to expect
  static Path<?> getPath(Path<?> rootPath, String propertyName) {
    String[] propertyPath = propertyName.split("\\.");
    return getPropertyPath(rootPath, propertyPath);
  }

  private static <T, P> Path<P> getPropertyPath(Path<T> rootPath, String[] propertyPath) {
    String firstLevelProperty = propertyPath[0];
    Path<P> firstLevelPropertyPath = getPropertyPath(rootPath, firstLevelProperty);
    if (propertyPath.length == 1)
      return firstLevelPropertyPath;
    else {
      String[] remainingPath = removeFirstElement(propertyPath);
      return getPropertyPath(firstLevelPropertyPath, remainingPath);
    }
  }

  private static String[] removeFirstElement(String[] propertyPath) {
    return Arrays.copyOfRange(propertyPath, 1, propertyPath.length);
  }

  private static <T, P> Path<P> getPropertyPath(Path<T> rootPath, String propertyName) {
    Path<P> propertyPath = rootPath.get(propertyName);
    if (isCollectionPath(rootPath, propertyPath))
      propertyPath = getJoin((From<?, T>) rootPath, propertyName);
    return propertyPath;
  }

  private static <P> boolean isCollectionPath(Path<?> rootPath, Path<P> propertyPath) {
    return PluralAttributePath.class.isAssignableFrom(propertyPath.getClass()) &&
        From.class.isAssignableFrom(rootPath.getClass());
  }

  private static <T, P> Join<T, P> getJoin(From<?, T> from, String joinProperty) {
    return from.join(joinProperty, JoinType.LEFT);
  }
}
