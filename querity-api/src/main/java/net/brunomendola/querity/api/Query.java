package net.brunomendola.querity.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Builder(toBuilder = true)
@Jacksonized
@Getter
@EqualsAndHashCode(of = {"filter", "pagination", "sort"})
@ToString(of = {"filter", "pagination", "sort"})
public class Query {
  private final Condition filter;
  private final Pagination pagination;
  @NonNull
  private final Sort[] sort;
  @NonNull
  @JsonIgnore
  private List<QueryPreprocessor> preprocessors;

  public boolean hasFilter() {
    return filter != null && !filter.isEmpty();
  }

  public boolean hasPagination() {
    return pagination != null;
  }

  public boolean hasSort() {
    return Arrays.stream(sort).anyMatch(s -> true);
  }

  public List<Sort> getSort() {
    return Arrays.asList(sort);
  }

  @NonNull List<QueryPreprocessor> getPreprocessors() {
    return preprocessors;
  }

  public static QueryBuilder builder() {
    return new QueryBuilder();
  }

  public static QueryBuilder builder(Query query) {
    return new QueryBuilder(query);
  }

  public static class QueryBuilder {
    private Condition filter;
    @SuppressWarnings("java:S1068")
    private Pagination pagination;
    @SuppressWarnings({"java:S1068", "java:S1450"})
    private Sort[] sort = new Sort[0];
    private List<QueryPreprocessor> preprocessors = new ArrayList<>();

    public QueryBuilder() {
    }

    public QueryBuilder(Query query) {
      if (query != null) {
        this.filter = query.filter;
        this.pagination = query.pagination;
        this.sort = query.sort;
        this.preprocessors = query.preprocessors;
      }
    }

    public QueryBuilder withPreprocessor(QueryPreprocessor preprocessor) {
      this.preprocessors.add(preprocessor);
      return this;
    }

    public QueryBuilder sort(Sort... sort) {
      this.sort = sort;
      return this;
    }

    public QueryBuilder pagination(Pagination pagination) {
      this.pagination = pagination;
      return this;
    }

    public QueryBuilder pagination(Integer page, Integer pageSize) {
      this.pagination = Querity.paged(page, pageSize);
      return this;
    }
  }

  public Query preprocess() {
    AtomicReference<Query> atomicQuery = new AtomicReference<>(this);
    this.getPreprocessors().forEach(p -> atomicQuery.set(p.preprocess(atomicQuery.get())));
    return atomicQuery.get();
  }
}
