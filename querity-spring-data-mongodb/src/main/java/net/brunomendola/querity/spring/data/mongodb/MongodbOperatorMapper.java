package net.brunomendola.querity.spring.data.mongodb;

import net.brunomendola.querity.api.Operator;
import net.brunomendola.querity.api.SimpleCondition;
import net.brunomendola.querity.common.util.PropertyUtils;
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

  private static Criteria getEquals(Criteria where, Object value, boolean negate) {
    return negate ? getNotEquals(where, value) : getEquals(where, value);
  }

  private static Criteria getEquals(Criteria where, Object value) {
    return where.is(value);
  }

  private static Criteria getNotEquals(Criteria where, Object value) {
    return where.ne(value);
  }

  private static Criteria getStartsWith(Criteria where, Object value, boolean negate) {
    return getRegex(where, "^" + value, negate);
  }

  private static Criteria getEndsWith(Criteria where, Object value, boolean negate) {
    return getRegex(where, value + "$", negate);
  }

  private static Criteria getRegex(Criteria where, Object value, boolean negate) {
    return negate ?
        where.not().regex(value.toString(), "i") :
        where.regex(value.toString(), "i");
  }

  @FunctionalInterface
  private interface MongodbOperatorCriteriaProvider {
    Criteria getCriteria(Criteria where, Object value, boolean negate);
  }

  public static <T> Criteria getCriteria(Class<T> entityClass, SimpleCondition condition, boolean negate) {
    String propertyPath = condition.getPropertyName();
    Criteria where = Criteria.where(propertyPath);
    Object value = PropertyUtils.getActualPropertyValue(entityClass, propertyPath, condition.getValue());
    return OPERATOR_CRITERIA_MAP.get(condition.getOperator())
        .getCriteria(where, value, negate);
  }
}
