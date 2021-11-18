package net.brunomendola.querity.api;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Getter
public class Sort {
  @NonNull
  private String propertyName;
  @Builder.Default
  @NonNull
  private Direction direction = Direction.ASC;

  public enum Direction {
    ASC, DESC
  }
}
