package net.brunomendola.querity.spring.data.jpa;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.experimental.Delegate;
import net.brunomendola.querity.api.NotCondition;

class JpaNotCondition extends JpaCondition {
  @Delegate
  private final NotCondition notCondition;

  public JpaNotCondition(NotCondition notCondition) {
    this.notCondition = notCondition;
  }

  @Override
  public <T> Predicate toPredicate(Class<T> entityClass, Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    return cb.not(
        cb.and( // work-around to make double-negation work (regression on Hibernate 6)
            JpaCondition.of(getCondition()).toPredicate(entityClass, root, cq, cb)));
  }
}
