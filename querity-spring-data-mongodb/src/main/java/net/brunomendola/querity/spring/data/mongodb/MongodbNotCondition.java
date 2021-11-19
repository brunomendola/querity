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
  public Criteria toCriteria(boolean negate) {
    return MongodbCondition.of(getCondition()).toCriteria(!negate);
  }
}
