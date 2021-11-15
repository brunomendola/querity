package net.brunomendola.querity.spring.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.brunomendola.querity.api.Query;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.lang.NonNull;
import org.springframework.validation.Validator;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

public class QuerityWebMvcSupport extends WebMvcConfigurationSupport {

  private final ObjectMapper objectMapper;

  public QuerityWebMvcSupport(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  @NonNull
  protected ConfigurableWebBindingInitializer getConfigurableWebBindingInitializer(@NonNull FormattingConversionService mvcConversionService, @NonNull Validator mvcValidator) {
    ConfigurableWebBindingInitializer initializer = super.getConfigurableWebBindingInitializer(mvcConversionService, mvcValidator);
    initializer.setPropertyEditorRegistrar(propertyEditorRegistry ->
        propertyEditorRegistry.registerCustomEditor(Query.class, new QueryPropertyEditor(objectMapper)));
    return initializer;
  }
}
