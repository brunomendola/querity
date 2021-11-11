package net.brunomendola.querity.spring.data.jpa;

import net.brunomendola.querity.api.Querity;
import net.brunomendola.querity.api.Query;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class QuerityJpaImpl implements Querity {

  private final EntityManager entityManager;

  public QuerityJpaImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public <T> List<T> findAll(Class<T> entityClass, Query query) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<T> cq = cb.createQuery(entityClass);
    cq.from(entityClass);
    TypedQuery<T> tq = entityManager.createQuery(cq);
    return tq.getResultList();
  }
}
