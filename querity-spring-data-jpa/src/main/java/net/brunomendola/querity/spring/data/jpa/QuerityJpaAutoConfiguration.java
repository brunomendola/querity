package net.brunomendola.querity.spring.data.jpa;

import net.brunomendola.querity.api.Querity;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;

public class QuerityJpaAutoConfiguration {
  @Bean
  public Querity querity(EntityManager entityManager) {
    return new QuerityJpaImpl(entityManager);
  }
}
