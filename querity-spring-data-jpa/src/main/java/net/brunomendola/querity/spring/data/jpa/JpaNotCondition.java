package net.brunomendola.querity.spring.data.jpa;

import lombok.experimental.Delegate;
import net.brunomendola.querity.api.NotCondition;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

class JpaNotCondition extends JpaCondition {
  @Delegate
  private final NotCondition notCondition;

  public JpaNotCondition(NotCondition notCondition) {
    this.notCondition = notCondition;
  }

  @Override
  public <T> Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    return cb.not(JpaCondition.of(getCondition()).toPredicate(root, cq, cb));
  }
}
