package net.brunomendola.querity.spring.data.mongodb;

import net.brunomendola.querity.api.OrConditionsWrapper;

class MongodbOrConditionsWrapper extends MongodbLogicConditionsWrapper {
  public MongodbOrConditionsWrapper(OrConditionsWrapper conditionsWrapper) {
    super(conditionsWrapper);
  }
}
