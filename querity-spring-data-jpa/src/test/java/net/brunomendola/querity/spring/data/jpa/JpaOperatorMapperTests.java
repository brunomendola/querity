package net.brunomendola.querity.spring.data.jpa;

import net.brunomendola.querity.api.Operator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JpaOperatorMapperTests {
  @Test
  void testAllOperatorsSupported() {
    assertThat(Operator.values())
        .allMatch(JpaOperatorMapper.OPERATOR_PREDICATE_MAP::containsKey);
  }
}
