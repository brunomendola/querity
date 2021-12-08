package net.brunomendola.querity.spring.data.jpa;

import net.brunomendola.querity.api.AndConditionsWrapper;

class JpaAndConditionsWrapper extends JpaLogicConditionsWrapper {
  public JpaAndConditionsWrapper(AndConditionsWrapper conditionsWrapper) {
    super(conditionsWrapper);
  }
}
