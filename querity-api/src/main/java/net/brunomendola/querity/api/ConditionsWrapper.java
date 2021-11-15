package net.brunomendola.querity.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConditionsWrapper implements Condition {
  @Builder.Default
  private LogicOperator logic = LogicOperator.AND;
  @Builder.Default
  private List<Condition> conditions = new ArrayList<>();
}
