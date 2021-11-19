package net.brunomendola.querity.spring.data.mongodb;

import lombok.experimental.Delegate;
import net.brunomendola.querity.api.ConditionsWrapper;
import net.brunomendola.querity.api.LogicOperator;
import org.springframework.data.mongodb.core.query.Criteria;

class MongodbConditionsWrapper extends MongodbCondition {
  @Delegate
  private final ConditionsWrapper conditionsWrapper;

  public MongodbConditionsWrapper(ConditionsWrapper conditionsWrapper) {
    this.conditionsWrapper = conditionsWrapper;
  }

  @Override
  public Criteria toCriteria(boolean negate) {
    Criteria[] conditionsCriteria = buildConditionsCriteria(negate);
    Criteria criteria = new Criteria();
    return getLogic().equals(LogicOperator.AND) ^ negate ? // xor
        criteria.andOperator(conditionsCriteria) :
        criteria.orOperator(conditionsCriteria);
  }

  private Criteria[] buildConditionsCriteria(boolean negate) {
    return getConditions().stream()
        .map(MongodbCondition::of)
        .map(c -> c.toCriteria(negate))
        .toArray(Criteria[]::new);
  }
}
