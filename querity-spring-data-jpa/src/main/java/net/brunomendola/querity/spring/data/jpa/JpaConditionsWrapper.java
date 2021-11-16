package net.brunomendola.querity.spring.data.jpa;

import lombok.experimental.Delegate;
import net.brunomendola.querity.api.ConditionsWrapper;
import net.brunomendola.querity.api.LogicOperator;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

class JpaConditionsWrapper implements JpaCondition {
  @Delegate
  private final ConditionsWrapper conditionsWrapper;

  public JpaConditionsWrapper(ConditionsWrapper conditionsWrapper) {
    this.conditionsWrapper = conditionsWrapper;
  }

  @Override
  public <T> Predicate toPredicate(Root<T> root, CriteriaQuery<T> cq, CriteriaBuilder cb) {
    return getLogicPredicate(getConditionPredicates(root, cq, cb), cb);
  }

  private <T> Predicate[] getConditionPredicates(Root<T> root, CriteriaQuery<T> cq, CriteriaBuilder cb) {
    return getConditions().stream()
        .map(JpaCondition::of)
        .map(c -> c.toPredicate(root, cq, cb))
        .toArray(Predicate[]::new);
  }

  private Predicate getLogicPredicate(Predicate[] conditionPredicates, CriteriaBuilder cb) {
    return getLogic().equals(LogicOperator.AND) ?
        cb.and(conditionPredicates) :
        cb.or(conditionPredicates);
  }
}
