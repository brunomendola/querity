package net.brunomendola.querity.common.util.valueextractor;

import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.util.stream.Stream;

class BooleanValueExtractorTests extends PropertyValueExtractorTests {

  @Override
  protected PropertyValueExtractor<?> getValueExtractor() {
    return new BooleanValueExtractor();
  }

  public static Stream<Arguments> provideTypesAndCanHandle() {
    return Stream.of(
        Arguments.of(BigDecimal.class, false),
        Arguments.of(Integer.class, false),
        Arguments.of(String.class, false),
        Arguments.of(Boolean.class, true)
    );
  }

  public static Stream<Arguments> provideInputAndExpectedExtractedValue() {
    return Stream.of(
        Arguments.of(null, null),
        Arguments.of("true", Boolean.TRUE),
        Arguments.of("false", Boolean.FALSE),
        Arguments.of(true, Boolean.TRUE),
        Arguments.of(false, Boolean.FALSE),
        Arguments.of(Boolean.TRUE, Boolean.TRUE),
        Arguments.of(Boolean.FALSE, Boolean.FALSE),
        Arguments.of("notABoolean", Boolean.FALSE)
    );
  }
}
