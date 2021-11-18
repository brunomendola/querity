package net.brunomendola.querity.api;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Getter
public class Pagination {
  @Builder.Default
  @NonNull
  private Integer page = 1;
  @NonNull
  private Integer pageSize;
}
