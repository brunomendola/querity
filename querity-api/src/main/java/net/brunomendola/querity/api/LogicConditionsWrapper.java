package net.brunomendola.querity.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@Getter
@AllArgsConstructor
public abstract class LogicConditionsWrapper implements Condition {
  @NonNull
  @JsonIgnore
  private final LogicOperator logic;
  @NonNull
  private final List<Condition> conditions;

  @Override
  public boolean isEmpty() {
    return this.conditions.isEmpty();
  }

}
