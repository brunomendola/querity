package net.brunomendola.querity.common.util.valueextractor;

import java.time.ZonedDateTime;

public class ZonedDateTimeValueExtractor implements PropertyValueExtractor<ZonedDateTime> {

  @Override
  public boolean canHandle(Class<?> propertyClass) {
    return isZonedDateTimeType(propertyClass);
  }

  @Override
  public ZonedDateTime extractValue(Object value) {
    if (value == null || isZonedDateTimeType(value.getClass()))
      return (ZonedDateTime) value;
    return getZonedDateTimeValue(value.toString());
  }

  private static boolean isZonedDateTimeType(Class<?> cls) {
    return ZonedDateTime.class.isAssignableFrom(cls);
  }

  private static ZonedDateTime getZonedDateTimeValue(String value) {
    return ZonedDateTime.parse(value);
  }
}
