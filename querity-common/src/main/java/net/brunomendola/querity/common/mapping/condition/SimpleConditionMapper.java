package net.brunomendola.querity.common.mapping.condition;

import net.brunomendola.querity.api.Condition;
import net.brunomendola.querity.api.SimpleCondition;
import net.brunomendola.querity.common.mapping.PropertyNameMapper;

class SimpleConditionMapper implements ConditionMapper<SimpleCondition> {
  @Override
  public boolean canMap(Condition condition) {
    return SimpleCondition.class.isAssignableFrom(condition.getClass());
  }

  @Override
  public SimpleCondition mapCondition(SimpleCondition condition, PropertyNameMapper propertyNameMapper) {
    return condition.toBuilder()
        .propertyName(propertyNameMapper.mapPropertyName(condition.getPropertyName()))
        .build();
  }
}
