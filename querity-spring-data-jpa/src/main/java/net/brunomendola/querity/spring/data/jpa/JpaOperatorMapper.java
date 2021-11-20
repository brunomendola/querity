package net.brunomendola.querity.spring.data.jpa;

import net.brunomendola.querity.api.Operator;
import net.brunomendola.querity.api.SimpleCondition;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.EnumMap;
import java.util.Map;

class JpaOperatorMapper {
  static final Map<Operator, JpaOperatorPredicateProvider> OPERATOR_PREDICATE_MAP = new EnumMap<>(Operator.class);

  static {
    OPERATOR_PREDICATE_MAP.put(Operator.EQUALS, JpaOperatorMapper::getEquals);
    OPERATOR_PREDICATE_MAP.put(Operator.NOT_EQUALS, JpaOperatorMapper::getNotEquals);
    OPERATOR_PREDICATE_MAP.put(Operator.STARTS_WITH, JpaOperatorMapper::getStartsWith);
    OPERATOR_PREDICATE_MAP.put(Operator.ENDS_WITH, JpaOperatorMapper::getEndsWith);
    OPERATOR_PREDICATE_MAP.put(Operator.CONTAINS, JpaOperatorMapper::getContains);
    OPERATOR_PREDICATE_MAP.put(Operator.IS_NULL, (path, value, cb) -> getIsNull(path, cb));
    OPERATOR_PREDICATE_MAP.put(Operator.IS_NOT_NULL, (path, value, cb) -> getIsNotNull(path, cb));
  }

  private static Predicate getIsNotNull(Path<?> path, CriteriaBuilder cb) {
    return cb.isNotNull(path);
  }

  private static Predicate getIsNull(Path<?> path, CriteriaBuilder cb) {
    return cb.isNull(path);
  }

  private static Predicate getNotEquals(Path<?> path, String value, CriteriaBuilder cb) {
    return cb.or(cb.notEqual(path, value), getIsNull(path, cb));
  }

  private static Predicate getEquals(Path<?> path, String value, CriteriaBuilder cb) {
    return cb.and(cb.equal(path, value), getIsNotNull(path, cb));
  }

  private static Predicate getStartsWith(Path<?> path, String value, CriteriaBuilder cb) {
    return getLike(path, value + "%", cb);
  }

  private static Predicate getEndsWith(Path<?> path, String value, CriteriaBuilder cb) {
    return getLike(path, "%" + value, cb);
  }

  private static Predicate getContains(Path<?> path, String value, CriteriaBuilder cb) {
    return getLike(path, "%" + value + "%", cb);
  }

  private static Predicate getLike(Path<?> path, String value, CriteriaBuilder cb) {
    return cb.like(cb.lower(path.as(String.class)), value.toLowerCase());
  }

  @FunctionalInterface
  private interface JpaOperatorPredicateProvider {
    Predicate getPredicate(Path<?> path, String value, CriteriaBuilder cb);
  }

  public static Predicate getPredicate(SimpleCondition condition, Root<?> root, CriteriaBuilder cb) {
    Path<?> propertyPath = JpaPropertyUtils.getPath(root, condition.getPropertyName());
    return OPERATOR_PREDICATE_MAP.get(condition.getOperator())
        .getPredicate(propertyPath, condition.getValue(), cb);
  }
}
