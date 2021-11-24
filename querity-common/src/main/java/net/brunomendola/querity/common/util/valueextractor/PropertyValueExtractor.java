package net.brunomendola.querity.common.util.valueextractor;

public interface PropertyValueExtractor<T> {
  boolean canHandle(Class<?> propertyClass);

  T extractValue(Object value);
}
