package net.brunomendola.querity.common.util.valueextractor;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PropertyValueExtractorFactoryTests {

  public static Stream<Arguments> provideBeanPropertyAndValueExtractor() {
    return Stream.of(
        Arguments.of(MyClass.class, "stringValue", StringValueExtractor.class),
        Arguments.of(MyClass.class, "intValue", NumericValueExtractor.class),
        Arguments.of(MyClass.class, "integerValue", NumericValueExtractor.class),
        Arguments.of(MyClass.class, "bigDecimalValue", NumericValueExtractor.class),
        Arguments.of(MyClass.class, "doubleValue", NumericValueExtractor.class),
        Arguments.of(MyClass.class, "primitiveBooleanValue", BooleanValueExtractor.class),
        Arguments.of(MyClass.class, "booleanValue", BooleanValueExtractor.class),
        Arguments.of(MyClass.class, "dateValue", DateValueExtractor.class),
        Arguments.of(MyClass.class, "localDateValue", LocalDateValueExtractor.class),
        Arguments.of(MyClass.class, "localDateTimeValue", LocalDateTimeValueExtractor.class),
        Arguments.of(MyClass.class, "zonedDateTimeValue", ZonedDateTimeValueExtractor.class)
    );
  }

  @ParameterizedTest
  @MethodSource("provideBeanPropertyAndValueExtractor")
  void givenBeanProperty_whenGetPropertyValueExtractor_thenReturnsCorrectValueExtractor(Class<?> beanClass, String propertyPath, Class<? extends PropertyValueExtractor<?>> expectedValueExtractorClass) {
    PropertyValueExtractor<?> valueExtractor = PropertyValueExtractorFactory.getPropertyValueExtractor(beanClass, propertyPath);
    assertThat(valueExtractor.getClass()).isEqualTo(expectedValueExtractorClass);
  }

  public static class MyClass {
    private String stringValue;
    private int intValue;
    private Integer integerValue;
    private BigDecimal bigDecimalValue;
    private double doubleValue;
    private boolean primitiveBooleanValue;
    private Boolean booleanValue;
    private Date dateValue;
    private LocalDate localDateValue;
    private LocalDateTime localDateTimeValue;
    private ZonedDateTime zonedDateTimeValue;
  }
}
