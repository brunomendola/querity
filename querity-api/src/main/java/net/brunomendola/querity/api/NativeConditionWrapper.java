package net.brunomendola.querity.api;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Builder(toBuilder = true)
@Jacksonized
@Getter
public class NativeConditionWrapper<T> implements Condition {
  @NonNull
  private T nativeCondition;
}
