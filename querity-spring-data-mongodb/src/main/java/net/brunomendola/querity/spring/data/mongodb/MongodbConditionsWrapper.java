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
  public <T> Criteria toCriteria(Class<T> entityClass, boolean negate) {
    Criteria[] conditionsCriteria = buildConditionsCriteria(entityClass, negate);
    Criteria criteria = new Criteria();
    return getLogic().equals(LogicOperator.AND) ^ negate ? // xor
        criteria.andOperator(conditionsCriteria) :
        criteria.orOperator(conditionsCriteria);
  }

  private <T> Criteria[] buildConditionsCriteria(Class<T> entityClass, boolean negate) {
    return getConditions().stream()
        .map(MongodbCondition::of)
        .map(c -> c.toCriteria(entityClass, negate))
        .toArray(Criteria[]::new);
  }
}
