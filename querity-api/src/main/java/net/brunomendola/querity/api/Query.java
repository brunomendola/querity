package net.brunomendola.querity.api;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Builder
@Jacksonized
@Getter
public class Query {
  private Condition filter;
  private Pagination pagination;
  @NonNull
  private List<Sort> sort;

  public boolean hasFilter() {
    return filter != null && !filter.isEmpty();
  }

  public boolean hasPagination() {
    return pagination != null;
  }

  public boolean hasSort() {
    return !sort.isEmpty();
  }

  public static class QueryBuilder {
    private List<Sort> sort = new ArrayList<>();

    public QueryBuilder sort(Sort... sort) {
      this.sort = Arrays.asList(sort);
      return this;
    }
  }
}
