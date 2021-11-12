package net.brunomendola.querity.spring.data.mongodb;

import lombok.experimental.Delegate;
import net.brunomendola.querity.api.Condition;
import org.springframework.data.mongodb.core.query.Criteria;

public class MongodbCondition {
  @Delegate
  private final Condition condition;

  MongodbCondition(Condition condition) {
    this.condition = condition;
  }

  Criteria toCriteria() {
    Criteria criteria = Criteria.where(getPropertyName());
    return criteria.is(getValue());
  }
}
