package net.brunomendola.querity.spring.data.jpa;

import net.brunomendola.querity.spring.data.jpa.domain.Person;
import net.brunomendola.querity.test.QuerityGenericSpringTestSuite;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

@Testcontainers
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create-drop")
class QuerityJpaImplTests extends QuerityGenericSpringTestSuite<Person> {

  public static final String POSTGRES_DOCKER_IMAGE = "postgres:14.1";

  @Container
  private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse(POSTGRES_DOCKER_IMAGE));

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
    registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
    registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
  }

  @Override
  protected void postImportEntities(List<Person> people) {
    people.forEach(p -> p.getAddress().setPerson(p));
  }
}
