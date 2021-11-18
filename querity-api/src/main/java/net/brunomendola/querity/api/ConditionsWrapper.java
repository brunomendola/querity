package net.brunomendola.querity.api;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;
import java.util.List;

@Builder
@Jacksonized
@Getter
public class ConditionsWrapper implements Condition {
  @Builder.Default
  @NonNull
  private LogicOperator logic = LogicOperator.AND;
  @Builder.Default
  @NonNull
  private List<Condition> conditions = new ArrayList<>();

  @Override
  public boolean isEmpty() {
    return this.conditions.isEmpty();
  }
}
