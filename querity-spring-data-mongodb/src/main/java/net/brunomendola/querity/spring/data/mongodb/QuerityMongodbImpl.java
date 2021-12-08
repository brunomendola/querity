package net.brunomendola.querity.spring.data.mongodb;

import net.brunomendola.querity.api.Condition;
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
    org.springframework.data.mongodb.core.query.Query q = getMongodbQuery(entityClass, query);
    return mongoTemplate.find(q, entityClass);
  }

  @Override
  public <T> Long count(Class<T> entityClass, Condition condition) {
    Query query = Querity.wrapConditionInQuery(condition);
    org.springframework.data.mongodb.core.query.Query q = getMongodbQuery(entityClass, query);
    return mongoTemplate.count(q, entityClass);
  }

  private <T> org.springframework.data.mongodb.core.query.Query getMongodbQuery(Class<T> entityClass, Query query) {
    return new MongodbQueryFactory<>(entityClass, query).getMongodbQuery();
  }
}
