package net.brunomendola.querity.spring.data.elasticsearch;

import lombok.experimental.Delegate;
import net.brunomendola.querity.api.NotCondition;
import org.springframework.data.elasticsearch.core.query.Criteria;

class ElasticsearchNotCondition extends ElasticsearchCondition {
  @Delegate
  private final NotCondition notCondition;

  public ElasticsearchNotCondition(NotCondition notCondition) {
    this.notCondition = notCondition;
  }

  @Override
  public <T> Criteria toCriteria(Class<T> entityClass, boolean negate) {
    return ElasticsearchCondition.of(getCondition()).toCriteria(entityClass, !negate);
  }
}
