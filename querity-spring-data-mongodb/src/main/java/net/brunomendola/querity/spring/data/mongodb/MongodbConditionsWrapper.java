package net.brunomendola.querity.spring.data.mongodb;

import lombok.experimental.Delegate;
import net.brunomendola.querity.api.ConditionsWrapper;
import net.brunomendola.querity.api.LogicOperator;
import org.springframework.data.mongodb.core.query.Criteria;

class MongodbConditionsWrapper implements MongodbCondition {
  @Delegate
  private final ConditionsWrapper conditionsWrapper;

  public MongodbConditionsWrapper(ConditionsWrapper conditionsWrapper) {
    this.conditionsWrapper = conditionsWrapper;
  }

  @Override
  public Criteria toCriteria() {
    return buildLogicCriteria(buildConditionsCriteria());
  }

  private Criteria[] buildConditionsCriteria() {
    return getConditions().stream()
        .map(MongodbCondition::of)
        .map(MongodbCondition::toCriteria)
        .toArray(Criteria[]::new);
  }

  private Criteria buildLogicCriteria(Criteria[] conditionsCriteria) {
    return getLogic().equals(LogicOperator.AND) ?
        new Criteria().andOperator(conditionsCriteria) :
        new Criteria().orOperator(conditionsCriteria);
  }
}
