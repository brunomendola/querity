package net.brunomendola.querity.common.mapping.condition;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.brunomendola.querity.api.Condition;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConditionMapperFactory {

  private static final List<ConditionMapper<?>> CONDITION_MAPPERS = Arrays.asList(
      new SimpleConditionMapper(), new AndConditionsWrapperMapper(),
      new OrConditionsWrapperMapper(), new NotConditionMapper()
  );

  @SuppressWarnings("unchecked")
  public static <C extends Condition> ConditionMapper<C> getConditionMapper(C condition) {
    return CONDITION_MAPPERS.stream()
        .filter(cm -> cm.canMap(condition))
        .findFirst()
        .map(cm -> (ConditionMapper<C>) cm)
        .orElseThrow(() -> new IllegalArgumentException("Condition mapper not found"));
  }
}
