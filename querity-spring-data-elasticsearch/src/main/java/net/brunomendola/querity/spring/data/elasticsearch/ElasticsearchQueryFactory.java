package net.brunomendola.querity.spring.data.elasticsearch;

import net.brunomendola.querity.api.Pagination;
import net.brunomendola.querity.api.Query;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.Criteria;

import java.util.List;
import java.util.stream.Collectors;

public class ElasticsearchQueryFactory<T> {
  private final Class<T> entityClass;
  private final Query query;

  ElasticsearchQueryFactory(Class<T> entityClass, Query query) {
    this.entityClass = entityClass;
    this.query = query;
  }

  org.springframework.data.elasticsearch.core.query.Query getElasticsearchQuery() {
    org.springframework.data.elasticsearch.core.query.Query q = initElasticsearchQuery();
    q = applyPaginationAndSorting(q);
    return q;
  }

  private org.springframework.data.elasticsearch.core.query.Query initElasticsearchQuery() {
    return query == null || !query.hasFilter() ?
        new org.springframework.data.elasticsearch.core.query.CriteriaQuery(new Criteria()) :
        new org.springframework.data.elasticsearch.core.query.CriteriaQuery(getElasticsearchCriteria());
  }

  private Criteria getElasticsearchCriteria() {
    return ElasticsearchCondition.of(query.getFilter()).toCriteria(entityClass);
  }

  private org.springframework.data.elasticsearch.core.query.Query applyPaginationAndSorting(org.springframework.data.elasticsearch.core.query.Query q) {
    return query != null && query.hasPagination() ?
        q.setPageable(getElasticsearchPageRequest()) :
        q.addSort(getElasticsearchSort());
  }

  private PageRequest getElasticsearchPageRequest() {
    Pagination pagination = query.getPagination();
    return PageRequest.of(
        pagination.getPage() - 1,
        pagination.getPageSize(),
        getElasticsearchSort());
  }

  private org.springframework.data.domain.Sort getElasticsearchSort() {
    return query == null || !query.hasSort() ?
        org.springframework.data.domain.Sort.unsorted() :
        org.springframework.data.domain.Sort.by(getElasticsearchSortOrder());
  }

  private List<Sort.Order> getElasticsearchSortOrder() {
    return query.getSort().stream()
        .map(ElasticsearchSort::new)
        .map(ElasticsearchSort::toElasticsearchSortOrder)
        .collect(Collectors.toList());
  }
}
