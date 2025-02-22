package net.brunomendola.querity.spring.data.jpa;

import jakarta.persistence.EntityManager;
import net.brunomendola.querity.api.Querity;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class QuerityJpaAutoConfiguration {
  @Bean
  public Querity querity(EntityManager entityManager) {
    return new QuerityJpaImpl(entityManager);
  }
}
