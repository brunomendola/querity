package net.brunomendola.querity.common.util.valueextractor;

import java.util.Arrays;
import java.util.List;

import static net.brunomendola.querity.common.util.PropertyUtils.getPropertyType;

public class PropertyValueExtractorFactory {
  public static final StringValueExtractor STRING_VALUE_EXTRACTOR = new StringValueExtractor();

  private static final List<PropertyValueExtractor<?>> extractors = Arrays.asList(
      STRING_VALUE_EXTRACTOR,
      new NumericValueExtractor());

  public static <T> PropertyValueExtractor<?> getPropertyValueExtractor(Class<T> beanClass, String propertyPath) {
    Class<?> propertyType = getPropertyType(beanClass, propertyPath);
    return extractors.stream()
        .filter(e -> e.canHandle(propertyType))
        .findAny()
        .orElse(STRING_VALUE_EXTRACTOR);
  }
}
