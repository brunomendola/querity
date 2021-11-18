package net.brunomendola.querity.api;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Getter
public class SimpleCondition implements Condition {
  @NonNull
  private String propertyName;
  @Builder.Default
  @NonNull
  private Operator operator = Operator.EQUALS;
  @NonNull
  private String value;
}
