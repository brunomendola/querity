package net.brunomendola.querity.spring.web;

import com.fasterxml.jackson.databind.Module;
import net.brunomendola.querity.spring.web.jackson.QuerityModule;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Import(QuerityWebMvcSupport.class)
@EnableAspectJAutoProxy
public class QueritySpringWebAutoConfiguration {
  @Bean
  public Module querityJacksonModule() {
    return new QuerityModule();
  }

  @Bean
  QuerityPreprocessorAspect querityPreprocessorAspect(
      ApplicationContext applicationContext) {
    return new QuerityPreprocessorAspect(applicationContext);
  }
}
