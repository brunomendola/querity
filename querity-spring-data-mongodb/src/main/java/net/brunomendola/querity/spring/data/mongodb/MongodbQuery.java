package net.brunomendola.querity.spring.data.mongodb;

import lombok.experimental.Delegate;
import net.brunomendola.querity.api.LogicOperator;
import net.brunomendola.querity.api.Query;
import org.springframework.data.mongodb.core.query.Criteria;

public class MongodbQuery {
  @Delegate
  private Query query;

  MongodbQuery(Query query) {
    this.query = query;
  }

  org.springframework.data.mongodb.core.query.Query toQuery() {
    if (getFilter().getConditions().isEmpty())
      return new org.springframework.data.mongodb.core.query.Query();
    Criteria[] conditionsCriteria = buildConditionsCriteria();
    return new org.springframework.data.mongodb.core.query.Query(buildLogicCriteria(conditionsCriteria));
  }

  private Criteria buildLogicCriteria(Criteria[] conditionsCriteria) {
    return getFilter().getLogic().equals(LogicOperator.AND) ?
        new Criteria().andOperator(conditionsCriteria) :
        new Criteria().orOperator(conditionsCriteria);
  }

  private Criteria[] buildConditionsCriteria() {
    return getFilter().getConditions().stream()
        .map(MongodbCondition::new)
        .map(MongodbCondition::toCriteria)
        .toArray(Criteria[]::new);
  }
}
