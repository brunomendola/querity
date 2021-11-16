package net.brunomendola.querity.spring.data.jpa;

import net.brunomendola.querity.api.Condition;
import net.brunomendola.querity.api.ConditionsWrapper;
import net.brunomendola.querity.api.SimpleCondition;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

interface JpaCondition {
  <T> Predicate toPredicate(Root<T> root, CriteriaQuery<T> cq, CriteriaBuilder cb);

  static JpaCondition of(Condition condition) {
    return condition instanceof ConditionsWrapper ?
        new JpaConditionsWrapper((ConditionsWrapper) condition) :
        new JpaSimpleCondition((SimpleCondition) condition);
  }
}
