package net.brunomendola.querity.api;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagination {
  @Builder.Default
  @NonNull
  private Integer page = 1;
  @NonNull
  private Integer pageSize;
}
