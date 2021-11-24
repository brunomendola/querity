package net.brunomendola.querity.common.util.valueextractor;

import java.time.LocalDate;

public class LocalDateValueExtractor implements PropertyValueExtractor<LocalDate> {

  @Override
  public boolean canHandle(Class<?> propertyClass) {
    return isLocalDateType(propertyClass);
  }

  @Override
  public LocalDate extractValue(Object value) {
    if (value == null || isLocalDateType(value.getClass()))
      return (LocalDate) value;
    return getLocalDateValue(value.toString());
  }

  private static boolean isLocalDateType(Class<?> cls) {
    return LocalDate.class.isAssignableFrom(cls);
  }

  private static LocalDate getLocalDateValue(String value) {
    return LocalDate.parse(value);
  }
}
