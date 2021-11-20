package net.brunomendola.querity.spring.data.mongodb;

import lombok.experimental.Delegate;
import net.brunomendola.querity.api.NotCondition;
import org.springframework.data.mongodb.core.query.Criteria;

public class MongodbNotCondition extends MongodbCondition {
  @Delegate
  private final NotCondition notCondition;

  public MongodbNotCondition(NotCondition notCondition) {
    this.notCondition = notCondition;
  }

  @Override
  public <T> Criteria toCriteria(Class<T> entityClass, boolean negate) {
    return MongodbCondition.of(getCondition()).toCriteria(entityClass, !negate);
  }
}
