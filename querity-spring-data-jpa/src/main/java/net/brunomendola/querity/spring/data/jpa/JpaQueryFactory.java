package net.brunomendola.querity.spring.data.jpa;

import net.brunomendola.querity.api.ConditionsWrapper;
import net.brunomendola.querity.api.Query;
import net.brunomendola.querity.api.SimpleCondition;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
    TypedQuery<T> tq = initJpaQuery(entityClass, entityManager);
    if (query.isPaginationSet())
      tq = applyPagination(tq);
    return tq;
  }

  private TypedQuery<T> initJpaQuery(Class<T> entityClass, EntityManager entityManager) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<T> cq = cb.createQuery(entityClass);
    Root<T> root = cq.from(entityClass);
    if (!query.isEmptyFilter())
      cq.where(getPredicate(root, cq, cb));
    return entityManager.createQuery(cq);
  }

  private Predicate getPredicate(Root<T> root, CriteriaQuery<T> cq, CriteriaBuilder cb) {
    return query.isFilterConditionsWrapper() ?
        new JpaConditionsWrapper((ConditionsWrapper) query.getFilter()).toPredicate(root, cq, cb) :
        new JpaSimpleCondition((SimpleCondition) query.getFilter()).toPredicate(root, cq, cb);
  }

  private TypedQuery<T> applyPagination(TypedQuery<T> tq) {
    return tq
        .setMaxResults(query.getPagination().getPageSize())
        .setFirstResult(query.getPagination().getPageSize() * (query.getPagination().getPage() - 1));
  }
}
