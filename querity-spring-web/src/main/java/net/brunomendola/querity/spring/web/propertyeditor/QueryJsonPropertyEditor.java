package net.brunomendola.querity.spring.web.propertyeditor;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.brunomendola.querity.api.Query;

public class QueryJsonPropertyEditor extends AbstractJsonPropertyEditor<Query> {
  public QueryJsonPropertyEditor(ObjectMapper objectMapper) {
    super(objectMapper);
  }
}
