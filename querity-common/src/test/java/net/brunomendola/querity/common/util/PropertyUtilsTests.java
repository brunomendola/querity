package net.brunomendola.querity.common.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PropertyUtilsTests {

  public static Stream<Arguments> provideBeanPropertiesAndTypes() {
    return Stream.of(
        Arguments.of(MyClass.class, "stringValue", String.class),
        Arguments.of(MyClass.class, "intValue", int.class),
        Arguments.of(MyClass.class, "integerValue", Integer.class),
        Arguments.of(MyClass.class, "bigDecimalValue", BigDecimal.class),
        Arguments.of(MyClass.class, "doubleValue", double.class),
        Arguments.of(MyClass.class, "nested.stringValue", String.class)
    );
  }

  @ParameterizedTest
  @MethodSource("provideBeanPropertiesAndTypes")
  void givenBeanProperty_whenGetPropertyType_thenReturnCorrectType(Class<?> beanClass, String propertyPath, Class<?> expectedPropertyType) {
    assertThat(PropertyUtils.getPropertyType(beanClass, propertyPath)).isEqualTo(expectedPropertyType);
  }

  public static class MyClass {
    private String stringValue;
    private int intValue;
    private Integer integerValue;
    private BigDecimal bigDecimalValue;
    private double doubleValue;
    private MyNestedClass nested;

    public static class MyNestedClass {
      private String stringValue;
    }
  }
}
