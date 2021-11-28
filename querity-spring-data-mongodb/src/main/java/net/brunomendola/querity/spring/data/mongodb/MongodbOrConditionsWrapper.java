package net.brunomendola.querity.spring.data.mongodb;

import net.brunomendola.querity.api.OrConditionsWrapper;

class MongodbOrConditionsWrapper extends MongodbConditionsWrapper {
  public MongodbOrConditionsWrapper(OrConditionsWrapper conditionsWrapper) {
    super(conditionsWrapper);
  }
}
