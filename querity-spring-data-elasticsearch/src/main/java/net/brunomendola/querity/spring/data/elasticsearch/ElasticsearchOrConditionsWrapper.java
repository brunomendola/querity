package net.brunomendola.querity.spring.data.elasticsearch;

import net.brunomendola.querity.api.OrConditionsWrapper;

class ElasticsearchOrConditionsWrapper extends ElasticsearchLogicConditionsWrapper {
  public ElasticsearchOrConditionsWrapper(OrConditionsWrapper conditionsWrapper) {
    super(conditionsWrapper);
  }
}
