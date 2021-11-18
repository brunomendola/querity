package net.brunomendola.querity.spring.data.mongodb;

import net.brunomendola.querity.api.Operator;
import net.brunomendola.querity.api.SimpleCondition;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.EnumMap;
import java.util.Map;

class MongodbOperatorMapper {
  static final Map<Operator, MongodbOperatorCriteriaProvider> OPERATOR_CRITERIA_MAP = new EnumMap<>(Operator.class);

  static {
    OPERATOR_CRITERIA_MAP.put(Operator.EQUALS,
        condition -> Criteria.where(condition.getPropertyName()).is(condition.getValue()));
    OPERATOR_CRITERIA_MAP.put(Operator.NOT_EQUALS,
        condition -> Criteria.where(condition.getPropertyName()).ne(condition.getValue()));
    OPERATOR_CRITERIA_MAP.put(Operator.IS_NULL,
        condition -> Criteria.where(condition.getPropertyName()).is(null));
    OPERATOR_CRITERIA_MAP.put(Operator.IS_NOT_NULL,
        condition -> Criteria.where(condition.getPropertyName()).ne(null));
  }

  @FunctionalInterface
  interface MongodbOperatorCriteriaProvider {
    Criteria getCriteria(SimpleCondition condition);
  }

  public static Criteria getCriteria(SimpleCondition condition) {
    return OPERATOR_CRITERIA_MAP.get(condition.getOperator()).getCriteria(condition);
  }
}
