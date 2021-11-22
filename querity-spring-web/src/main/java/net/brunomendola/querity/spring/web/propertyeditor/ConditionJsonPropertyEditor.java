package net.brunomendola.querity.spring.web.propertyeditor;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.brunomendola.querity.api.Condition;

public class ConditionJsonPropertyEditor extends AbstractJsonPropertyEditor<Condition> {
  public ConditionJsonPropertyEditor(ObjectMapper objectMapper) {
    super(objectMapper);
  }
}
