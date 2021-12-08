package net.brunomendola.querity.common.mapping.condition;

import net.brunomendola.querity.api.Condition;
import net.brunomendola.querity.api.OrConditionsWrapper;
import net.brunomendola.querity.common.mapping.PropertyNameMapper;

import java.util.stream.Collectors;

class OrConditionsWrapperMapper implements ConditionMapper<OrConditionsWrapper> {
  @Override
  public boolean canMap(Condition condition) {
    return OrConditionsWrapper.class.isAssignableFrom(condition.getClass());
  }

  @Override
  public OrConditionsWrapper mapCondition(OrConditionsWrapper condition, PropertyNameMapper propertyNameMapper) {
    return condition.toBuilder()
        .conditions(condition.getConditions().stream()
            .map(c -> ConditionMapperFactory.getConditionMapper(c)
                .mapCondition(c, propertyNameMapper))
            .collect(Collectors.toList()))
        .build();
  }
}
