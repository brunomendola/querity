package net.brunomendola.querity.api;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Getter
public class SimpleCondition implements Condition {
  @NonNull
  private String propertyName;
  @Builder.Default
  @NonNull
  private Operator operator = Operator.EQUALS;
  private String value;

  @Builder
  @Jacksonized
  public SimpleCondition(@NonNull String propertyName, @NonNull Operator operator, String value) {
    validate(operator, value);
    this.propertyName = propertyName;
    this.operator = operator;
    this.value = value;
  }

  private void validate(@NonNull Operator operator, String value) {
    if (operator.getRequiredValuesCount() != getValuesCount(value))
      throw new IllegalArgumentException(
          String.format("The operator %s requires %d value(s)", operator, operator.getRequiredValuesCount()));
  }

  private int getValuesCount(String value) {
    return value == null ? 0 : 1;
  }
}
