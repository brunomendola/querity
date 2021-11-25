package net.brunomendola.querity.spring.data.mongodb;

import net.brunomendola.querity.spring.data.mongodb.domain.Person;
import net.brunomendola.querity.test.QuerityGenericSpringTestSuite;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Comparator;

@Testcontainers
class QuerityMongodbImplTests extends QuerityGenericSpringTestSuite<Person, String> {

  public static final String MONGO_DB_DOCKER_IMAGE = "mongo:5.0.4";

  @Container
  private static final MongoDBContainer MONGO_DB_CONTAINER = new MongoDBContainer(DockerImageName.parse(MONGO_DB_DOCKER_IMAGE));

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.mongodb.uri", MONGO_DB_CONTAINER::getReplicaSetUrl);
  }

  @Override
  protected Class<Person> getEntityClass() {
    return Person.class;
  }

  /**
   * Overridden because sort with nulls last is not supported in MongoDB
   */
  @Override
  protected <C extends Comparable<? super C>> Comparator<C> getSortComparator() {
    return Comparator.nullsFirst(Comparator.naturalOrder());
  }
}
