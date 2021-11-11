package net.brunomendola.querity.spring.data.mongodb;

import net.brunomendola.querity.api.Querity;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

public class QuerityMongodbAutoConfiguration {
  @Bean
  public Querity querity(MongoTemplate mongoTemplate) {
    return new QuerityMongodbImpl(mongoTemplate);
  }
}
