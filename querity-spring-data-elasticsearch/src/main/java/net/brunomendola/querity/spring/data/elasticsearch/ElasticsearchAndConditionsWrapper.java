package net.brunomendola.querity.spring.data.elasticsearch;

import net.brunomendola.querity.api.AndConditionsWrapper;

class ElasticsearchAndConditionsWrapper extends ElasticsearchLogicConditionsWrapper {
  public ElasticsearchAndConditionsWrapper(AndConditionsWrapper conditionsWrapper) {
    super(conditionsWrapper);
  }
}
