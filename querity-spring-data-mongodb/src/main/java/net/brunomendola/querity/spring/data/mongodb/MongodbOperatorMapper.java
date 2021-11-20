package net.brunomendola.querity.spring.data.mongodb;

import net.brunomendola.querity.api.Operator;
import net.brunomendola.querity.api.SimpleCondition;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.EnumMap;
import java.util.Map;

class MongodbOperatorMapper {
  static final Map<Operator, MongodbOperatorCriteriaProvider> OPERATOR_CRITERIA_MAP = new EnumMap<>(Operator.class);

  static {
    OPERATOR_CRITERIA_MAP.put(Operator.EQUALS, MongodbOperatorMapper::getEquals);
    OPERATOR_CRITERIA_MAP.put(Operator.NOT_EQUALS, (where, value, negate) -> getEquals(where, value, !negate));
    OPERATOR_CRITERIA_MAP.put(Operator.STARTS_WITH, MongodbOperatorMapper::getStartsWith);
    OPERATOR_CRITERIA_MAP.put(Operator.ENDS_WITH, MongodbOperatorMapper::getEndsWith);
    OPERATOR_CRITERIA_MAP.put(Operator.CONTAINS, MongodbOperatorMapper::getRegex);
    OPERATOR_CRITERIA_MAP.put(Operator.IS_NULL, (where, value, negate) -> getEquals(where, null, negate));
    OPERATOR_CRITERIA_MAP.put(Operator.IS_NOT_NULL, (where, value, negate) -> getEquals(where, null, !negate));
  }

  private static Criteria getEquals(Criteria where, String value, boolean negate) {
    return negate ? getNotEquals(where, value) : getEquals(where, value);
  }

  private static Criteria getEquals(Criteria where, String value) {
    return where.is(value);
  }

  private static Criteria getNotEquals(Criteria where, String value) {
    return where.ne(value);
  }

  private static Criteria getStartsWith(Criteria where, String value, boolean negate) {
    return getRegex(where, "^" + value, negate);
  }

  private static Criteria getEndsWith(Criteria where, String value, boolean negate) {
    return getRegex(where, value + "$", negate);
  }

  private static Criteria getRegex(Criteria where, String value, boolean negate) {
    return negate ?
        where.not().regex(value, "i") :
        where.regex(value, "i");
  }

  @FunctionalInterface
  private interface MongodbOperatorCriteriaProvider {
    Criteria getCriteria(Criteria where, String value, boolean negate);
  }

  public static Criteria getCriteria(SimpleCondition condition, boolean negate) {
    Criteria where = Criteria.where(condition.getPropertyName());
    return OPERATOR_CRITERIA_MAP.get(condition.getOperator())
        .getCriteria(where, condition.getValue(), negate);
  }
}
