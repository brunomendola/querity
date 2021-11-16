package net.brunomendola.querity.spring.data.mongodb;

import net.brunomendola.querity.api.ConditionsWrapper;
import net.brunomendola.querity.api.Query;
import net.brunomendola.querity.api.SimpleCondition;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;
import java.util.stream.Collectors;

class MongodbQueryFactory {
  private final Query query;

  MongodbQueryFactory(Query query) {
    this.query = query;
  }

  org.springframework.data.mongodb.core.query.Query getMongodbQuery() {
    org.springframework.data.mongodb.core.query.Query q = initMongodbQuery();
    q = applyPaginationAndSorting(q);
    return q;
  }

  private org.springframework.data.mongodb.core.query.Query initMongodbQuery() {
    return !query.hasFilter() ?
        new org.springframework.data.mongodb.core.query.Query() :
        new org.springframework.data.mongodb.core.query.Query(getMongodbCriteria());
  }

  private Criteria getMongodbCriteria() {
    return query.isSimpleConditionFilter() ?
        new MongodbSimpleCondition((SimpleCondition) query.getFilter()).toCriteria() :
        new MongodbConditionsWrapper((ConditionsWrapper) query.getFilter()).toCriteria();
  }

  private org.springframework.data.mongodb.core.query.Query applyPaginationAndSorting(org.springframework.data.mongodb.core.query.Query q) {
    return query.hasPagination() ?
        q.with(getMongodbPageRequest()) :
        q.with(getMongodbSort());
  }

  private PageRequest getMongodbPageRequest() {
    return PageRequest.of(
        query.getPagination().getPage() - 1,
        query.getPagination().getPageSize(),
        getMongodbSort());
  }

  private org.springframework.data.domain.Sort getMongodbSort() {
    return !query.hasSort() ?
        org.springframework.data.domain.Sort.unsorted() :
        org.springframework.data.domain.Sort.by(getMongoDbSortOrder());
  }

  private List<Sort.Order> getMongoDbSortOrder() {
    return query.getSort().stream()
        .map(MongodbSort::new)
        .map(MongodbSort::toMongoSortOrder)
        .collect(Collectors.toList());
  }
}
