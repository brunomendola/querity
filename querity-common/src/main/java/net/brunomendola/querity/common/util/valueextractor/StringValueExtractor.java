package net.brunomendola.querity.common.util.valueextractor;

public class StringValueExtractor implements PropertyValueExtractor<String> {
  @Override
  public boolean canHandle(Class<?> propertyClass) {
    return String.class.equals(propertyClass);
  }

  @Override
  public String extractValue(String value) {
    return value;
  }
}
