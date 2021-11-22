package net.brunomendola.querity.spring.data.jpa;

import lombok.experimental.Delegate;
import net.brunomendola.querity.api.SimpleCondition;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

class JpaSimpleCondition extends JpaCondition {
  @Delegate
  private final SimpleCondition condition;

  JpaSimpleCondition(SimpleCondition condition) {
    this.condition = condition;
  }

  @Override
  public <T> Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    return JpaOperatorMapper.getPredicate(condition, root, cb);
  }
}
