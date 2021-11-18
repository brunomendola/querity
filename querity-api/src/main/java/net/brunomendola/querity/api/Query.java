package net.brunomendola.querity.api;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;
import java.util.List;

@Builder
@Jacksonized
@Getter
public class Query {
  private Condition filter;
  private Pagination pagination;
  @Builder.Default
  @NonNull
  private List<Sort> sort = new ArrayList<>();

  public boolean isSimpleConditionFilter() {
    return filter instanceof SimpleCondition;
  }

  public boolean hasFilter() {
    return filter != null && !filter.isEmpty();
  }

  public boolean hasPagination() {
    return pagination != null;
  }

  public boolean hasSort() {
    return !sort.isEmpty();
  }
}
