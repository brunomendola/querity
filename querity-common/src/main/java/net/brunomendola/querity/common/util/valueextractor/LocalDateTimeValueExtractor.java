package net.brunomendola.querity.common.util.valueextractor;

import java.time.LocalDateTime;

public class LocalDateTimeValueExtractor implements PropertyValueExtractor<LocalDateTime> {

  @Override
  public boolean canHandle(Class<?> propertyClass) {
    return isLocalDateTimeType(propertyClass);
  }

  @Override
  public LocalDateTime extractValue(Object value) {
    if (value == null || isLocalDateTimeType(value.getClass()))
      return (LocalDateTime) value;
    return getLocalDateTimeValue(value.toString());
  }

  private static boolean isLocalDateTimeType(Class<?> cls) {
    return LocalDateTime.class.isAssignableFrom(cls);
  }

  private static LocalDateTime getLocalDateTimeValue(String value) {
    return LocalDateTime.parse(value);
  }
}
