package net.brunomendola.querity.spring.data.elasticsearch;

import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import lombok.extern.slf4j.Slf4j;
import net.brunomendola.querity.api.Condition;
import net.brunomendola.querity.api.Querity;
import net.brunomendola.querity.api.Query;
import org.springframework.data.elasticsearch.UncategorizedElasticsearchException;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;

@Slf4j
public class QuerityElasticsearchImpl implements Querity {

  private final ElasticsearchOperations elasticsearchOperations;

  public QuerityElasticsearchImpl(ElasticsearchOperations elasticsearchOperations) {
    this.elasticsearchOperations = elasticsearchOperations;
  }

  @Override
  public <T> List<T> findAll(Class<T> entityClass, Query query) {
    org.springframework.data.elasticsearch.core.query.Query q = getElasticsearchQuery(entityClass, query);
    try {
      SearchHits<T> hits = elasticsearchOperations.search(q, entityClass);
      return hits.stream().map(SearchHit::getContent).toList();
    } catch (UncategorizedElasticsearchException e) {
      log.error(((ElasticsearchException) e.getCause()).response().error().rootCause().get(0).reason());
      throw e;
    }
  }

  @Override
  public <T> Long count(Class<T> entityClass, Condition condition) {
    Query query = Querity.wrapConditionInQuery(condition);
    org.springframework.data.elasticsearch.core.query.Query q = getElasticsearchQuery(entityClass, query);
    return elasticsearchOperations.count(q, entityClass);
  }

  private <T> org.springframework.data.elasticsearch.core.query.Query getElasticsearchQuery(Class<T> entityClass, Query query) {
    return new ElasticsearchQueryFactory<>(entityClass, query).getElasticsearchQuery();
  }
}
