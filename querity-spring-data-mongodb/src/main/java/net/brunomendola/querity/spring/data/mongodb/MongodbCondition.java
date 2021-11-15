package net.brunomendola.querity.spring.data.mongodb;

import net.brunomendola.querity.api.Condition;
import net.brunomendola.querity.api.ConditionsWrapper;
import net.brunomendola.querity.api.SimpleCondition;
import org.springframework.data.mongodb.core.query.Criteria;

interface MongodbCondition {
  Criteria toCriteria();

  static MongodbCondition of(Condition condition) {
    return condition instanceof ConditionsWrapper ?
        new MongodbConditionsWrapper((ConditionsWrapper) condition) :
        new MongodbSimpleCondition((SimpleCondition) condition);
  }
}
