package net.brunomendola.querity.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Getter
public class NotCondition implements Condition {
  @NonNull
  @JsonProperty("not")
  private Condition condition;

  @Override
  public boolean isEmpty() {
    return this.condition.isEmpty();
  }
}
