package net.brunomendola.querity.spring.data.mongodb;

import lombok.experimental.Delegate;
import net.brunomendola.querity.api.ConditionsWrapper;
import net.brunomendola.querity.api.Query;
import net.brunomendola.querity.api.SimpleCondition;
import org.springframework.data.mongodb.core.query.Criteria;

class MongodbQuery {
  @Delegate
  private Query query;

  MongodbQuery(Query query) {
    this.query = query;
  }

  org.springframework.data.mongodb.core.query.Query toQuery() {
    if (isEmptyFilter())
      return new org.springframework.data.mongodb.core.query.Query();
    return new org.springframework.data.mongodb.core.query.Query(getCriteria());
  }

  private Criteria getCriteria() {
    return isFilterConditionsWrapper() ?
        new MongodbConditionsWrapper((ConditionsWrapper) getFilter()).toCriteria() :
        new MongodbSimpleCondition((SimpleCondition) getFilter()).toCriteria();
  }
}
