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
    OPERATOR_PREDICATE_MAP.put(Operator.EQUALS,
        (path, condition, cb) -> cb.equal(path, condition.getValue()));
    OPERATOR_PREDICATE_MAP.put(Operator.NOT_EQUALS,
        (path, condition, cb) -> cb.or(cb.notEqual(path, condition.getValue()), cb.isNull(path)));
    OPERATOR_PREDICATE_MAP.put(Operator.IS_NULL,
        (path, condition, cb) -> cb.isNull(path));
    OPERATOR_PREDICATE_MAP.put(Operator.IS_NOT_NULL,
        (path, condition, cb) -> cb.isNotNull(path));
  }

  @FunctionalInterface
  interface JpaOperatorPredicateProvider {
    Predicate getPredicate(Path<?> path, SimpleCondition condition, CriteriaBuilder cb);
  }

  public static Predicate getPredicate(SimpleCondition condition, Root<?> root, CriteriaBuilder cb) {
    Path<?> propertyPath = JpaPropertyUtils.getPath(root, condition.getPropertyName());
    return OPERATOR_PREDICATE_MAP.get(condition.getOperator()).getPredicate(propertyPath, condition, cb);
  }
}
