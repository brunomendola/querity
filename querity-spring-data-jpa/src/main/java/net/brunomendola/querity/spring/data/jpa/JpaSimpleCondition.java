package net.brunomendola.querity.spring.data.jpa;

import lombok.experimental.Delegate;
import net.brunomendola.querity.api.SimpleCondition;

import javax.persistence.criteria.*;
import java.util.Arrays;

class JpaSimpleCondition implements JpaCondition {
  @Delegate
  private final SimpleCondition condition;

  JpaSimpleCondition(SimpleCondition condition) {
    this.condition = condition;
  }

  @Override
  public <T> Predicate toPredicate(Root<T> root, CriteriaQuery<T> cq, CriteriaBuilder cb) {
    String[] propertyPath = getPropertyName().split("\\.");
    return cb.equal(getPath(root, propertyPath), getValue());
  }

  private Path<?> getPath(Path<?> rootPath, String[] propertyPath) {
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
