package net.brunomendola.querity.common.mapping.condition;

import net.brunomendola.querity.api.Condition;
import net.brunomendola.querity.common.mapping.PropertyNameMapper;

public interface ConditionMapper<C extends Condition> {
  boolean canMap(Condition c);

  C mapCondition(C condition, PropertyNameMapper propertyNameMapper);
}
