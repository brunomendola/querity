package net.brunomendola.querity.common.util.valueextractor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

class NumericValueExtractorTests extends AbstractPropertyValueExtractorTests {

  @Override
  protected PropertyValueExtractor<?> getValueExtractor() {
    return new NumericValueExtractor();
  }

  public static Stream<Arguments> provideTypesAndCanHandle() {
    return Stream.of(
        Arguments.of(BigDecimal.class, true),
        Arguments.of(Integer.class, true),
        Arguments.of(String.class, false),
        Arguments.of(Boolean.class, false)
    );
  }

  public static Stream<Arguments> provideInputAndExpectedExtractedValue() {
    return Stream.of(
        Arguments.of(null, null),
        Arguments.of("11", 11L),
        Arguments.of("0.12", new BigDecimal("0.12")),
        Arguments.of("0", 0L),
        Arguments.of("42.00", new BigDecimal("42.00")),
        Arguments.of(new BigDecimal("420.00"), new BigDecimal("420.00")),
        Arguments.of(420, 420),
        Arguments.of((short) 1, (short) 1)
    );
  }

  @Test
  void givenInvalidNumber_whenExtractValue_thenThrowIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class,
        () -> valueExtractor.extractValue("notANumber"),
        "Not a number: notANumber");

  }
}
