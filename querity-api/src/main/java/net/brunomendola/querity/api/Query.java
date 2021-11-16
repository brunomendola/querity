package net.brunomendola.querity.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Query {
  private Condition filter;
  private Pagination pagination;

  public boolean isFilterConditionsWrapper() {
    return filter instanceof ConditionsWrapper;
  }

  public boolean isEmptyFilter() {
    return filter == null ||
        filter instanceof ConditionsWrapper && ((ConditionsWrapper) filter).getConditions().isEmpty();
  }

  public boolean isPaginationSet() {
    return pagination != null;
  }
}
