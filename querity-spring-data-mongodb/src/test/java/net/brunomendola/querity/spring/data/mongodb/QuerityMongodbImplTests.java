package net.brunomendola.querity.spring.data.mongodb;

import net.brunomendola.querity.spring.data.mongodb.domain.Person;
import net.brunomendola.querity.test.QuerityGenericSpringTestSuite;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static net.brunomendola.querity.spring.data.mongodb.TestData.getPeople;

@Testcontainers
class QuerityMongodbImplTests extends QuerityGenericSpringTestSuite<Person> {

  @Container
  private static final MongoDBContainer MONGO_DB_CONTAINER = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.mongodb.uri", MONGO_DB_CONTAINER::getReplicaSetUrl);
  }

  @Override
  protected List<Person> getEntities() {
    return getPeople();
  }
}
