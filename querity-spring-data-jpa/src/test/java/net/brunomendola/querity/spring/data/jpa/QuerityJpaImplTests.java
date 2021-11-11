package net.brunomendola.querity.spring.data.jpa;

import net.brunomendola.querity.spring.data.jpa.domain.Person;
import net.brunomendola.querity.test.QuerityGenericSpringTestSuite;

import java.util.List;

import static net.brunomendola.querity.spring.data.jpa.TestData.getPeople;

class QuerityJpaImplTests extends QuerityGenericSpringTestSuite<Person> {
  @Override
  protected List<Person> getEntities() {
    return getPeople();
  }
}
