package net.brunomendola.querity.spring.data.mongodb;

import net.brunomendola.querity.api.AndConditionsWrapper;

class MongodbAndConditionsWrapper extends MongodbConditionsWrapper {
  public MongodbAndConditionsWrapper(AndConditionsWrapper conditionsWrapper) {
    super(conditionsWrapper);
  }
}
