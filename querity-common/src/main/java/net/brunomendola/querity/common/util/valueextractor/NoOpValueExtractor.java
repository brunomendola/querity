package net.brunomendola.querity.common.util.valueextractor;

public class NoOpValueExtractor implements PropertyValueExtractor<Object> {
  @Override
  public boolean canHandle(Class<?> propertyType) {
    return false;
  }

  @Override
  public Object extractValue(Class<?> propertyType, Object value) {
    return value;
  }
}
