package net.brunomendola.querity.spring.data.jpa;

import org.springframework.test.context.TestPropertySource;

import java.util.Comparator;

@TestPropertySource(properties = {
    "spring.datasource.driver-class-name=org.h2.Driver"
})
class QuerityH2JpaImplTests extends QuerityJpaImplTests {

  /**
   * Overridden because sort behaves differently in H2 regarding null values
   */
  @Override
  protected <C extends Comparable<? super C>> Comparator<C> getSortComparator() {
    return Comparator.nullsFirst(Comparator.naturalOrder());
  }
}
