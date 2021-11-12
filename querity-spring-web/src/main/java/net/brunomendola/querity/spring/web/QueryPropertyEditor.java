package net.brunomendola.querity.spring.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.brunomendola.querity.api.Query;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;

public class QueryPropertyEditor extends PropertyEditorSupport {
  private final ObjectMapper objectMapper;

  public QueryPropertyEditor(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public void setAsText(String text) throws IllegalArgumentException {
    if (!StringUtils.hasText(text)) {
      setValue(null);
    } else {
      Query query;
      try {
        query = objectMapper.readValue(text, Query.class);
      } catch (JsonProcessingException e) {
        throw new IllegalArgumentException(e);
      }
      setValue(query);
    }
  }

}
