package net.brunomendola.querity.common.util.valueextractor;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PropertyValueExtractorFactory {
  public static final StringValueExtractor STRING_VALUE_EXTRACTOR = new StringValueExtractor();

  private static final List<PropertyValueExtractor<?>> extractors = Arrays.asList(
      STRING_VALUE_EXTRACTOR,
      new NumericValueExtractor(),
      new BooleanValueExtractor(),
      new DateValueExtractor(),
      new LocalDateValueExtractor(),
      new LocalDateTimeValueExtractor(),
      new ZonedDateTimeValueExtractor(),
      new EnumValueExtractor());

  @SuppressWarnings("java:S1452")
  public static PropertyValueExtractor<?> getPropertyValueExtractor(Class<?> propertyType) {
    return extractors.stream()
        .filter(e -> e.canHandle(propertyType))
        .findAny()
        .orElse(STRING_VALUE_EXTRACTOR);
  }
}
