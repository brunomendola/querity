package net.brunomendola.querity.spring.data.jpa;

import lombok.experimental.Delegate;
import net.brunomendola.querity.api.SimpleCondition;

import javax.persistence.criteria.*;

class JpaSimpleCondition implements JpaCondition {
  @Delegate
  private final SimpleCondition condition;

  JpaSimpleCondition(SimpleCondition condition) {
    this.condition = condition;
  }

  @Override
  public <T> Predicate toPredicate(Root<T> root, CriteriaQuery<T> cq, CriteriaBuilder cb) {
    Path<?> path = JpaPropertyUtils.getPath(root, getPropertyName());
    return cb.equal(path, getValue());
  }

}
