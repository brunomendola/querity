package net.brunomendola.querity.spring.data.jpa;

import lombok.experimental.Delegate;
import net.brunomendola.querity.api.ConditionsWrapper;
import net.brunomendola.querity.api.Query;
import net.brunomendola.querity.api.SimpleCondition;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

class JpaQuery {
  @Delegate
  private final Query query;

  JpaQuery(Query query) {
    this.query = query;
  }

  public <T> Optional<Predicate> toPredicate(Root<T> root, CriteriaQuery<T> cq, CriteriaBuilder cb) {
    if (isEmptyFilter())
      return Optional.empty();
    return Optional.of(getPredicate(root, cq, cb));
  }

  private <T> Predicate getPredicate(Root<T> root, CriteriaQuery<T> cq, CriteriaBuilder cb) {
    return isFilterConditionsWrapper() ?
        new JpaConditionsWrapper((ConditionsWrapper) getFilter()).toPredicate(root, cq, cb) :
        new JpaSimpleCondition((SimpleCondition) getFilter()).toPredicate(root, cq, cb);
  }
}
