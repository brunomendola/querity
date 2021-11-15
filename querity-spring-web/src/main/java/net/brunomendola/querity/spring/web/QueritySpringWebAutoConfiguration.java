package net.brunomendola.querity.spring.web;

import com.fasterxml.jackson.databind.Module;
import net.brunomendola.querity.spring.web.jackson.QuerityModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(QuerityWebMvcSupport.class)
public class QueritySpringWebAutoConfiguration {
  @Bean
  public Module querityJacksonModule() {
    return new QuerityModule();
  }
}
