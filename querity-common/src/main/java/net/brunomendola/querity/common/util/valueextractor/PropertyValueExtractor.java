package net.brunomendola.querity.common.util.valueextractor;

public interface PropertyValueExtractor<T> {
  boolean canHandle(Class<?> propertyType);

  T extractValue(Class<?> propertyType, Object value);
}
