package net.brunomendola.querity.spring.data.jpa;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.Path;
import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class JpaPropertyUtils {
  @SuppressWarnings("java:S1452") // we don't really know what type to expect
  static Path<?> getPath(Path<?> rootPath, String propertyName) {
    String[] propertyPath = propertyName.split("\\.");
    return getPath(rootPath, propertyPath);
  }

  private static Path<?> getPath(Path<?> rootPath, String[] propertyPath) {
    String firstLevelProperty = propertyPath[0];
    Path<Object> firstLevelPropertyPath = rootPath.get(firstLevelProperty);
    if (propertyPath.length == 1)
      return firstLevelPropertyPath;
    else {
      String[] remainingPath = Arrays.copyOfRange(propertyPath, 1, propertyPath.length);
      return getPath(firstLevelPropertyPath, remainingPath);
    }
  }
}
