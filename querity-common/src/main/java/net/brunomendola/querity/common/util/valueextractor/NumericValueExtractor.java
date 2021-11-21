package net.brunomendola.querity.common.util.valueextractor;

import lombok.SneakyThrows;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NumericValueExtractor implements PropertyValueExtractor<Number> {

  @Override
  public boolean canHandle(Class<?> propertyClass) {
    return isNumericType(propertyClass);
  }

  @Override
  public Number extractValue(Object value) {
    if (isNumericType(value.getClass()))
      return getNumber(value);
    return getNumericValue(value.toString());
  }

  @SneakyThrows
  private Number getNumber(Object numericTypeValue) {
    Class<?> valueClass = numericTypeValue.getClass();
    if (!valueClass.isPrimitive()) return (Number) numericTypeValue;
    Class<?> wrapperClass = ClassUtils.primitiveToWrapper(valueClass);
    return (Number) wrapperClass.getConstructor(valueClass).newInstance(numericTypeValue);
  }

  private static final Set<Class<?>> PRIMITIVE_NUMBERS = Stream
      .of(int.class, long.class, float.class,
          double.class, byte.class, short.class)
      .collect(Collectors.toSet());

  private static boolean isNumericType(Class<?> cls) {
    if (cls.isPrimitive()) {
      return PRIMITIVE_NUMBERS.contains(cls);
    } else {
      return Number.class.isAssignableFrom(cls);
    }
  }

  private static Number getNumericValue(String value) {
    if (value == null) return null;
    if (!NumberUtils.isParsable(value)) throw new IllegalArgumentException(String.format("Not a number: %s", value));
    return isFloatingPointNumber(value) ?
        new BigDecimal(value) :
        Long.valueOf(value);
  }

  private static boolean isFloatingPointNumber(String value) {
    return value.contains(".");
  }
}