package net.brunomendola.querity.spring.data.elasticsearch;

import net.brunomendola.querity.api.Querity;
import net.brunomendola.querity.api.Query;
import net.brunomendola.querity.spring.data.elasticsearch.domain.Person;
import net.brunomendola.querity.test.QuerityGenericSpringTestSuite;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static net.brunomendola.querity.api.Querity.filterByNative;
import static net.brunomendola.querity.api.Querity.not;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = QuerityElasticsearchTestApplication.class)
@Testcontainers
public class QuerityElasticsearchImplTests extends QuerityGenericSpringTestSuite<Person, String> {

  private static final String ELASTICSEARCH_IMAGE = "docker.elastic.co/elasticsearch/elasticsearch:8.16.5";

  @Container
  private static final ElasticsearchContainer ELASTICSEARCH_CONTAINER = new ElasticsearchContainer(DockerImageName.parse(ELASTICSEARCH_IMAGE))
      .withEnv("xpack.security.enabled", "false");

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.elasticsearch.uris", ELASTICSEARCH_CONTAINER::getHttpHostAddress);
  }

  @Override
  protected Class<Person> getEntityClass() {
    return Person.class;
  }

  @Override
  protected <C extends Comparable<? super C>> Comparator<C> getSortComparator() {
    return Comparator.nullsFirst(Comparator.naturalOrder());
  }

  @Test
  void givenElasticsearchNativeCondition_whenFilterAll_thenReturnOnlyFilteredElements() {
    Criteria criteria = Criteria.where("lastName").is(entity1.getLastName());
    Query query = Querity.query()
        .filter(filterByNative(criteria))
        .build();
    List<Person> result = querity.findAll(Person.class, query);
    assertThat(result)
        .isNotEmpty()
        .isEqualTo(entities.stream()
            .filter(p -> entity1.getLastName().equals(p.getLastName()))
            .collect(Collectors.toList()));
  }

  @Test
  void givenNotConditionWrappingElasticsearchNativeCondition_whenFilterAll_thenThrowIllegalArgumentException() {
    Criteria criteria = Criteria.where("lastName").is(entity1.getLastName());
    Query query = Querity.query()
        .filter(not(filterByNative(criteria)))
        .build();
    assertThrows(IllegalArgumentException.class,
        () -> querity.findAll(Person.class, query),
        "Not conditions wrapping native conditions is not supported; just write a negative native condition.");
  }
}
