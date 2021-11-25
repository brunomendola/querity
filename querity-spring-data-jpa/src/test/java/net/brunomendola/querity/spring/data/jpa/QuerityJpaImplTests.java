package net.brunomendola.querity.spring.data.jpa;

import net.brunomendola.querity.spring.data.jpa.domain.Person;
import net.brunomendola.querity.test.QuerityGenericSpringTestSuite;

public abstract class QuerityJpaImplTests extends QuerityGenericSpringTestSuite<Person, Long> {
  @Override
  protected Class<Person> getEntityClass() {
    return Person.class;
  }
}
