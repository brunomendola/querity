package net.brunomendola.querity.common.mapping;

import net.brunomendola.querity.api.Condition;
import net.brunomendola.querity.api.Query;
import net.brunomendola.querity.api.QueryPreprocessor;
import net.brunomendola.querity.api.Sort;
import net.brunomendola.querity.common.mapping.condition.ConditionMapperFactory;

public class PropertyNameMappingPreprocessor implements QueryPreprocessor {

  private final PropertyNameMapper propertyNameMapper;

  public PropertyNameMappingPreprocessor(PropertyNameMapper propertyNameMapper) {
    this.propertyNameMapper = propertyNameMapper;
  }

  @Override
  public Query preprocess(Query query) {
    return query.toBuilder()
        .filter(mapCondition(query.getFilter()))
        .sort(query.getSort().stream()
            .map(this::mapSort)
            .toArray(Sort[]::new))
        .build();
  }

  private Condition mapCondition(Condition condition) {
    if (condition == null) return null;
    return ConditionMapperFactory.getConditionMapper(condition)
        .mapCondition(condition, propertyNameMapper);
  }

  private Sort mapSort(Sort sort) {
    return sort.toBuilder()
        .propertyName(propertyNameMapper.mapPropertyName(sort.getPropertyName()))
        .build();
  }
}
