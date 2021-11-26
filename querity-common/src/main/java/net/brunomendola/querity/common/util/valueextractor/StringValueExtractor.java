package net.brunomendola.querity.common.util.valueextractor;

public class StringValueExtractor implements PropertyValueExtractor<String> {
  @Override
  public boolean canHandle(Class<?> propertyType) {
    return String.class.equals(propertyType);
  }

  @Override
  public String extractValue(Class<?> propertyType, Object value) {
    return value.toString();
  }
}
