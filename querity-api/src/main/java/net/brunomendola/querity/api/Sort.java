package net.brunomendola.querity.api;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
