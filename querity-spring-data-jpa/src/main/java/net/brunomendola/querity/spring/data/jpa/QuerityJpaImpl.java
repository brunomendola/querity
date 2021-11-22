package net.brunomendola.querity.spring.data.jpa;

import net.brunomendola.querity.api.Condition;
import net.brunomendola.querity.api.Querity;
import net.brunomendola.querity.api.Query;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class QuerityJpaImpl implements Querity {

  private final EntityManager entityManager;

  public QuerityJpaImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public <T> List<T> findAll(Class<T> entityClass, Query query) {
    TypedQuery<T> jpaQuery = getJpaQueryFactory(entityClass, query).getJpaQuery();
    return jpaQuery.getResultList();
  }

  @Override
  public <T> Long count(Class<T> entityClass, Condition condition) {
    Query query = Querity.query().filter(condition).build();
    TypedQuery<Long> jpaQuery = getJpaQueryFactory(entityClass, query).getJpaCountQuery();
    return jpaQuery.getSingleResult();
  }

  private <T> JpaQueryFactory<T> getJpaQueryFactory(Class<T> entityClass, Query query) {
    return new JpaQueryFactory<>(entityClass, query, entityManager);
  }
}
