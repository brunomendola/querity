package net.brunomendola.querity.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.brunomendola.querity.common.util.valueextractor.PropertyValueExtractorFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static net.brunomendola.querity.common.util.ReflectionUtils.getAccessibleField;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PropertyUtils {

  public static <T> Class<?> getPropertyType(Class<T> beanClass, String propertyPath) {
    return getPropertyType(beanClass, Arrays.asList(propertyPath.split("\\.")));
  }

  private static <T> Class<?> getPropertyType(Class<T> beanClass, List<String> propertyPath) {
    String fieldName = propertyPath.get(0);
    Class<?> fieldType = getFieldType(beanClass, fieldName);
    if (propertyPath.size() == 1) {
      return fieldType;
    } else {
      return getPropertyType(fieldType, propertyPath.subList(1, propertyPath.size()));
    }
  }

  private static <T> Class<?> getFieldType(Class<T> beanClass, String fieldName) {
    return getAccessibleField(beanClass, fieldName)
        .map(Field::getType)
        .orElseThrow(() -> new IllegalArgumentException(
            String.format("Property %s not found in class %s", fieldName, beanClass.getSimpleName())));
  }

  public static <T> Object getActualPropertyValue(Class<T> beanClass, String propertyPath, String value) {
    if (value == null) return null;
    return PropertyValueExtractorFactory.getPropertyValueExtractor(beanClass, propertyPath).extractValue(value);
  }
}
