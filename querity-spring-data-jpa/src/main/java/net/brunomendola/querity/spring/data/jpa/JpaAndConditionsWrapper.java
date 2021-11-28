package net.brunomendola.querity.spring.data.jpa;

import net.brunomendola.querity.api.AndConditionsWrapper;

class JpaAndConditionsWrapper extends JpaConditionsWrapper {
  public JpaAndConditionsWrapper(AndConditionsWrapper conditionsWrapper) {
    super(conditionsWrapper);
  }
}
