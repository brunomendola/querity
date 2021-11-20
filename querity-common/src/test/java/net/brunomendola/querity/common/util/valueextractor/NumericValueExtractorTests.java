package net.brunomendola.querity.common.util.valueextractor;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class NumericValueExtractorTests {

  private final NumericValueExtractor valueExtractor = new NumericValueExtractor();

  public static Stream<Arguments> provideClassesAndCanHandle() {
    return Stream.of(
        Arguments.of(BigDecimal.class, true),
        Arguments.of(Integer.class, true),
        Arguments.of(String.class, false),
        Arguments.of(Boolean.class, false)
    );
  }

  @ParameterizedTest
  @MethodSource("provideClassesAndCanHandle")
  void givenClass_whenCanHandle_thenReturnTrueIfNumeric(Class<?> clazz, boolean expectedCanHandle) {
    assertThat(valueExtractor.canHandle(clazz)).isEqualTo(expectedCanHandle);
  }

  public static Stream<Arguments> provideStringAndNumericValue() {
    return Stream.of(
        Arguments.of("11", 11L),
        Arguments.of("0.12", new BigDecimal("0.12")),
        Arguments.of("0", 0L),
        Arguments.of("42.00", new BigDecimal("42.00"))
    );
  }

  @ParameterizedTest
  @MethodSource("provideStringAndNumericValue")
  void givenNumericString_whenExtractValue_thenReturnTheNumber(String string, Number expectedNumber) {
    assertThat(valueExtractor.extractValue(string)).isEqualTo(expectedNumber);
  }
}
