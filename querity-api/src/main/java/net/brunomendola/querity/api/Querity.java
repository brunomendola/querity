package net.brunomendola.querity.api;

import java.util.Arrays;
import java.util.List;

public interface Querity {
  <T> List<T> findAll(Class<T> entityClass, Query query);

  <T> Long count(Class<T> entityClass, Condition condition);

  static Query.QueryBuilder query() {
    return Query.builder();
  }

  /**
   * Filter by propertyName EQUALS value
   *
   * @param propertyName the property name
   * @param value        the value
   * @return a SimpleCondition with {@link Operator#EQUALS}
   */
  static SimpleCondition filterBy(String propertyName, Object value) {
    return SimpleCondition.builder()
        .propertyName(propertyName).value(value).build();
  }

  static SimpleCondition filterBy(String propertyName, Operator operator, Object value) {
    return SimpleCondition.builder()
        .propertyName(propertyName).operator(operator).value(value).build();
  }

  static SimpleCondition filterBy(String propertyName, Operator operator) {
    return SimpleCondition.builder()
        .propertyName(propertyName).operator(operator).build();
  }

  static ConditionsWrapper and(Condition... conditions) {
    return ConditionsWrapper.builder().conditions(Arrays.asList(conditions)).build();
  }

  static ConditionsWrapper or(Condition... conditions) {
    return ConditionsWrapper.builder().logic(LogicOperator.OR).conditions(Arrays.asList(conditions)).build();
  }

  static NotCondition not(Condition condition) {
    return NotCondition.builder().condition(condition).build();
  }

  static Pagination paged(Integer page, Integer pageSize) {
    return Pagination.builder().page(page).pageSize(pageSize).build();
  }

  static Sort sortBy(String propertyName) {
    return sortBy(propertyName, Sort.Direction.ASC);
  }

  static Sort sortBy(String propertyName, Sort.Direction direction) {
    return Sort.builder().propertyName(propertyName).direction(direction).build();
  }
}
