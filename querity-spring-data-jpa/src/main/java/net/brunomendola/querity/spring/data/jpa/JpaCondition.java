package net.brunomendola.querity.spring.data.jpa;

import lombok.experimental.Delegate;
import net.brunomendola.querity.api.Condition;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class JpaCondition {
  @Delegate
  private final Condition condition;

  JpaCondition(Condition condition) {
    this.condition = condition;
  }

  <T> Predicate toPredicate(Root<T> root, CriteriaQuery<T> cq, CriteriaBuilder cb) {
    return cb.equal(root.get(getPropertyName()), getValue());
  }
}
