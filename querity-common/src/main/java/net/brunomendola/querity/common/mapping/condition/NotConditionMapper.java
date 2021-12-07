package net.brunomendola.querity.common.mapping.condition;

import net.brunomendola.querity.api.Condition;
import net.brunomendola.querity.api.NotCondition;
import net.brunomendola.querity.common.mapping.PropertyNameMapper;

class NotConditionMapper implements ConditionMapper<NotCondition> {
  @Override
  public boolean canMap(Condition condition) {
    return NotCondition.class.isAssignableFrom(condition.getClass());
  }

  @Override
  public NotCondition mapCondition(NotCondition condition, PropertyNameMapper propertyNameMapper) {
    Condition c = condition.getCondition();
    return condition.toBuilder()
        .condition(ConditionMapperFactory.getConditionMapper(c)
            .mapCondition(c, propertyNameMapper))
        .build();
  }
}
