package net.brunomendola.querity.spring.data.jpa;

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
    TypedQuery<T> jpaQuery = new JpaQueryFactory<>(entityClass, query, entityManager).getJpaQuery();
    return jpaQuery.getResultList();
  }
}
