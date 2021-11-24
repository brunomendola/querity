package net.brunomendola.querity.common.util.valueextractor;

import java.time.Instant;
import java.util.Date;

public class DateValueExtractor implements PropertyValueExtractor<Date> {

  @Override
  public boolean canHandle(Class<?> propertyClass) {
    return isDateType(propertyClass);
  }

  @Override
  public Date extractValue(Object value) {
    if (value == null || isDateType(value.getClass()))
      return (Date) value;
    return getDateValue(value.toString());
  }

  private static boolean isDateType(Class<?> cls) {
    return Date.class.isAssignableFrom(cls);
  }

  private static Date getDateValue(String value) {
    return Date.from(Instant.parse(value));
  }
}
