package net.brunomendola.querity.spring.web;

import net.brunomendola.querity.api.QueryPreprocessor;
import net.brunomendola.querity.common.mapping.PropertyNameMappingPreprocessor;
import net.brunomendola.querity.common.mapping.SimplePropertyNameMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class QueritySpringWebTestApplication {
  public static void main(String[] args) {
    SpringApplication.run(QueritySpringWebTestApplication.class, args);
  }

  @Bean
  QueryPreprocessor preprocessor1() {
    return new PropertyNameMappingPreprocessor(SimplePropertyNameMapper.builder()
        .mapping("prop1", "prop2")
        .build());
  }
}
