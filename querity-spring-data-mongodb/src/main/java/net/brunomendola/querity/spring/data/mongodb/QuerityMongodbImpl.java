package net.brunomendola.querity.spring.data.mongodb;

import net.brunomendola.querity.api.Querity;
import net.brunomendola.querity.api.Query;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

public class QuerityMongodbImpl implements Querity {

  private final MongoTemplate mongoTemplate;

  public QuerityMongodbImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public <T> List<T> findAll(Class<T> entityClass, Query query) {
    return mongoTemplate.find(new org.springframework.data.mongodb.core.query.Query(), entityClass);
  }
}
