package net.brunomendola.querity.spring.data.jpa;

import net.brunomendola.querity.api.Querity;
import net.brunomendola.querity.api.Query;
import net.brunomendola.querity.spring.data.jpa.domain.Person;
import net.brunomendola.querity.test.QuerityGenericSpringTestSuite;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.stream.Collectors;

import static net.brunomendola.querity.api.Querity.filterByNative;
import static net.brunomendola.querity.api.Querity.not;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = QuerityJpaTestApplication.class)
public abstract class QuerityJpaImplTests extends QuerityGenericSpringTestSuite<Person, Long> {
  @Override
  protected Class<Person> getEntityClass() {
    return Person.class;
  }

  @Test
  void givenJpaNativeCondition_whenFilterAll_thenReturnOnlyFilteredElements() {
    Specification<Person> specification = (root, cq, cb) -> cb.equal(root.get("lastName"), entity1.getLastName());
    Query query = Querity.query()
        .filter(filterByNative(specification))
        .build();
    List<Person> result = querity.findAll(getEntityClass(), query);
    assertThat(result).isNotEmpty();
    assertThat(result).isEqualTo(entities.stream()
        .filter(p -> entity1.getLastName().equals(p.getLastName()))
        .collect(Collectors.toList()));
  }

  @Test
  void givenNotConditionWrappingJpaNativeCondition_whenFilterAll_thenReturnOnlyFilteredElements() {
    Specification<Person> specification = (root, cq, cb) -> cb.and(
        root.get("lastName").isNotNull(),
        cb.equal(root.get("lastName"), entity1.getLastName())
    );
    Query query = Querity.query()
        .filter(not(filterByNative(specification)))
        .build();
    List<Person> result = querity.findAll(getEntityClass(), query);
    assertThat(result).isNotEmpty();
    assertThat(result).isEqualTo(entities.stream()
        .filter(p -> !entity1.getLastName().equals(p.getLastName()))
        .collect(Collectors.toList()));
  }
}
