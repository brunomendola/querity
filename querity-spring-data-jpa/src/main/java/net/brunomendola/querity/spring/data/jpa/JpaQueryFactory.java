package net.brunomendola.querity.spring.data.jpa;

import net.brunomendola.querity.api.ConditionsWrapper;
import net.brunomendola.querity.api.Query;
import net.brunomendola.querity.api.SimpleCondition;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.stream.Collectors;

class JpaQueryFactory<T> {
  private final Class<T> entityClass;
  private final Query query;
  private final EntityManager entityManager;

  JpaQueryFactory(Class<T> entityClass, Query query, EntityManager entityManager) {
    this.entityClass = entityClass;
    this.query = query;
    this.entityManager = entityManager;
  }

  public TypedQuery<T> getJpaQuery() {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<T> cq = cb.createQuery(entityClass);
    Root<T> root = cq.from(entityClass);

    if (query.hasFilter())
      cq.where(getPredicate(root, cq, cb));

    if (query.hasSort()) {
      cq.orderBy(getOrder(root, cb));
    }

    TypedQuery<T> tq = entityManager.createQuery(cq);

    if (query.hasPagination())
      tq = applyPagination(tq);

    return tq;
  }

  private Predicate getPredicate(Root<T> root, CriteriaQuery<T> cq, CriteriaBuilder cb) {
    return query.isSimpleConditionFilter() ?
        new JpaSimpleCondition((SimpleCondition) query.getFilter()).toPredicate(root, cq, cb) :
        new JpaConditionsWrapper((ConditionsWrapper) query.getFilter()).toPredicate(root, cq, cb);
  }

  private List<Order> getOrder(Root<T> root, CriteriaBuilder cb) {
    return query.getSort().stream()
        .map(JpaSort::new)
        .map(jpaSort -> jpaSort.toOrder(root, cb))
        .collect(Collectors.toList());
  }

  private TypedQuery<T> applyPagination(TypedQuery<T> tq) {
    return tq
        .setMaxResults(query.getPagination().getPageSize())
        .setFirstResult(query.getPagination().getPageSize() * (query.getPagination().getPage() - 1));
  }
}
