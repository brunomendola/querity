package net.brunomendola.querity.spring.data.jpa;

import net.brunomendola.querity.spring.data.jpa.domain.Person;
import net.brunomendola.querity.test.QuerityGenericSpringTestSuite;

import java.util.List;

public abstract class QuerityJpaImplTests extends QuerityGenericSpringTestSuite<Person> {
  @Override
  protected void postImportEntities(List<Person> people) {
    people.forEach(p -> p.getAddress().setPerson(p));
  }
}
