package net.brunomendola.querity.test;

import lombok.SneakyThrows;
import net.brunomendola.querity.api.*;
import net.brunomendola.querity.test.domain.Person;
import net.brunomendola.querity.test.domain.PersonRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.GenericTypeResolver;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public abstract class QuerityGenericSpringTestSuite<T extends Person<?>> {
  public final List<T> entities = getEntities();

  @Autowired
  PersonRepository<T, ?> repository;

  @Autowired
  Querity querity;

  @BeforeEach
  void setUp() {
    repository.saveAll(entities);
  }

  @AfterEach
  void tearDown() {
    repository.deleteAll();
  }

  @Test
  void givenEmptyFilter_whenFilterAll_thenReturnAllTheElements() {
    Query query = Query.builder()
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize(entities.size());
  }

  @Test
  void givenStringEqualsFilter_whenFilterAll_thenReturnOnlyFilteredElements() {
    Query query = Query.builder()
        .filter(SimpleCondition.builder().propertyName("lastName").operator(Operator.EQUALS).value("Skywalker").build())
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize((int) entities.stream().filter(p -> p.getLastName().equals("Skywalker")).count());
  }

  @Test
  void givenTwoStringEqualsFiltersWithAndLogic_whenFilterAll_thenReturnOnlyFilteredElements() {
    Query query = Query.builder()
        .filter(ConditionsWrapper.builder()
            .conditions(Arrays.asList(
                SimpleCondition.builder().propertyName("lastName").operator(Operator.EQUALS).value("Skywalker").build(),
                SimpleCondition.builder().propertyName("firstName").operator(Operator.EQUALS).value("Luke").build()
            ))
            .build())
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize((int) entities.stream()
        .filter(p -> p.getLastName().equals("Skywalker") && p.getFirstName().equals("Luke"))
        .count());
  }

  @Test
  void givenTwoStringEqualsFiltersWithOrLogic_whenFilterAll_thenReturnOnlyFilteredElements() {
    Query query = Query.builder()
        .filter(ConditionsWrapper.builder()
            .logic(LogicOperator.OR)
            .conditions(Arrays.asList(
                SimpleCondition.builder().propertyName("lastName").operator(Operator.EQUALS).value("Skywalker").build(),
                SimpleCondition.builder().propertyName("lastName").operator(Operator.EQUALS).value("Kenobi").build()
            ))
            .build())
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize((int) entities.stream()
        .filter(p -> p.getLastName().equals("Skywalker") || p.getLastName().equals("Kenobi"))
        .count());
  }

  @Test
  void givenFilterWithNestedConditions_whenFindAll_thenReturnListOfEntity() {
    Query query = Query.builder()
        .filter(ConditionsWrapper.builder()
            .conditions(Arrays.asList(
                SimpleCondition.builder().propertyName("lastName").operator(Operator.EQUALS).value("Skywalker").build(),
                ConditionsWrapper.builder()
                    .logic(LogicOperator.OR)
                    .conditions(Arrays.asList(
                        SimpleCondition.builder().propertyName("firstName").operator(Operator.EQUALS).value("Anakin").build(),
                        SimpleCondition.builder().propertyName("firstName").operator(Operator.EQUALS).value("Luke").build()
                    ))
                    .build()
            ))
            .build())
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize((int) entities.stream()
        .filter(p -> p.getLastName().equals("Skywalker") && (p.getFirstName().equals("Anakin") || p.getFirstName().equals("Luke")))
        .count());
  }

  @Test
  void givenNestedFieldEqualsFilter_whenFilterAll_thenReturnOnlyFilteredElements() {
    Query query = Query.builder()
        .filter(SimpleCondition.builder().propertyName("address.city").operator(Operator.EQUALS).value("Tatooine").build())
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize((int) entities.stream().filter(p -> p.getAddress().getCity().equals("Tatooine")).count());
  }

  @Test
  void givenPagination_whenFilterAll_thenReturnThePageElements() {
    Query query = Query.builder()
        .pagination(Pagination.builder().page(2).pageSize(3).build())
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize((int) entities.stream().skip(3).limit(3).count());
  }

  @SneakyThrows
  private List<T> getEntities() {
    List<T> entities = CsvUtils.readCsv("/querity/test-data.csv", getEntityClass());
    postImportEntities(entities);
    return entities;
  }

  protected void postImportEntities(List<T> entities) {
    // do nothing by default
  }

  @SuppressWarnings("unchecked")
  private Class<T> getEntityClass() {
    return (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), QuerityGenericSpringTestSuite.class);
  }
}
