package net.brunomendola.querity.spring.data.mongodb;

import net.brunomendola.querity.api.ConditionsWrapper;
import net.brunomendola.querity.api.Query;
import net.brunomendola.querity.api.SimpleCondition;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;

class MongodbQueryFactory<T> {
  private final Class<T> entityClass;
  private final Query query;

  MongodbQueryFactory(Class<T> entityClass, Query query) {
    this.entityClass = entityClass;
    this.query = query;
  }

  org.springframework.data.mongodb.core.query.Query getMongodbQuery() {
    org.springframework.data.mongodb.core.query.Query q = initMongodbQuery();
    if (query.isPaginationSet())
      q = applyPagination(q);
    return q;
  }

  private org.springframework.data.mongodb.core.query.Query initMongodbQuery() {
    return query.isEmptyFilter() ?
        new org.springframework.data.mongodb.core.query.Query() :
        new org.springframework.data.mongodb.core.query.Query(getCriteria());
  }

  private Criteria getCriteria() {
    return query.isFilterConditionsWrapper() ?
        new MongodbConditionsWrapper((ConditionsWrapper) query.getFilter()).toCriteria() :
        new MongodbSimpleCondition((SimpleCondition) query.getFilter()).toCriteria();
  }

  private org.springframework.data.mongodb.core.query.Query applyPagination(org.springframework.data.mongodb.core.query.Query q) {
    return q.with(PageRequest.of(
        query.getPagination().getPage() - 1,
        query.getPagination().getPageSize()));
  }
}
