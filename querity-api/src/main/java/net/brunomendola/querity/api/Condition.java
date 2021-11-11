package net.brunomendola.querity.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Condition {
  private String propertyName;
  @Builder.Default
  private Operator operator = Operator.EQUALS;
  private String value;
}
