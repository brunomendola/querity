package net.brunomendola.querity.spring.data.jpa;

import lombok.experimental.Delegate;
import net.brunomendola.querity.api.LogicOperator;
import net.brunomendola.querity.api.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class JpaQuery {
  @Delegate
  private final Query query;

  JpaQuery(Query query) {
    this.query = query;
  }

  public <T> Optional<Predicate> toPredicate(Root<T> root, CriteriaQuery<T> cq, CriteriaBuilder cb) {
    if (getFilter().getConditions().isEmpty())
      return Optional.empty();
    return Optional.of(
        getLogicPredicate(
            getConditionPredicates(root, cq, cb),
            cb));
  }

  private <T> Predicate[] getConditionPredicates(Root<T> root, CriteriaQuery<T> cq, CriteriaBuilder cb) {
    return getFilter().getConditions().stream()
        .map(JpaCondition::new)
        .map(c -> c.toPredicate(root, cq, cb))
        .toArray(Predicate[]::new);
  }

  private Predicate getLogicPredicate(Predicate[] conditionPredicates, CriteriaBuilder cb) {
    return getFilter().getLogic().equals(LogicOperator.AND) ?
        cb.and(conditionPredicates) :
        cb.or(conditionPredicates);
  }
}
