package net.brunomendola.querity.spring.data.jpa;

import net.brunomendola.querity.spring.data.jpa.domain.Person;
import net.brunomendola.querity.test.QuerityGenericSpringTestSuite;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = QuerityJpaTestApplication.class)
public abstract class QuerityJpaImplTests extends QuerityGenericSpringTestSuite<Person, Long> {
  @Override
  protected Class<Person> getEntityClass() {
    return Person.class;
  }
}
