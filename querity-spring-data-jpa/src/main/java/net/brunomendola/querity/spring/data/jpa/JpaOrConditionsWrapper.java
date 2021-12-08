package net.brunomendola.querity.spring.data.jpa;

import net.brunomendola.querity.api.OrConditionsWrapper;

class JpaOrConditionsWrapper extends JpaLogicConditionsWrapper {
  public JpaOrConditionsWrapper(OrConditionsWrapper conditionsWrapper) {
    super(conditionsWrapper);
  }
}
