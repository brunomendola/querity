package net.brunomendola.querity.test;

import lombok.SneakyThrows;
import net.brunomendola.querity.api.Querity;
import net.brunomendola.querity.api.Query;
import net.brunomendola.querity.test.domain.Person;
import net.brunomendola.querity.test.domain.PersonRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.GenericTypeResolver;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static net.brunomendola.querity.api.Operator.*;
import static net.brunomendola.querity.api.Querity.*;
import static net.brunomendola.querity.api.Sort.Direction.DESC;
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
    Query query = Querity.query()
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize(6);
    assertThat(result).isEqualTo(entities);
  }

  @Test
  void givenFilterWithStringEqualsCondition_whenFilterAll_thenReturnOnlyFilteredElements() {
    Query query = Querity.query()
        .filter(filterBy("lastName", EQUALS, "Skywalker"))
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize(2);
    assertThat(result).isEqualTo(entities.stream().filter(p -> "Skywalker".equals(p.getLastName())).collect(Collectors.toList()));
  }

  @Test
  void givenFilterWithIntegerEqualsCondition_whenFilterAll_thenReturnOnlyFilteredElements() {
    Query query = Querity.query()
        .filter(filterBy("children", EQUALS, 2))
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize(1);
    assertThat(result).isEqualTo(entities.stream()
        .filter(p -> p.getChildren() != null && p.getChildren().equals(2))
        .collect(Collectors.toList()));
  }

  @Test
  void givenFilterWithIntegerEqualsConditionAsString_whenFilterAll_thenReturnOnlyFilteredElements() {
    Query query = Querity.query()
        .filter(filterBy("children", EQUALS, "2"))
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize(1);
    assertThat(result).isEqualTo(entities.stream()
        .filter(p -> p.getChildren() != null && p.getChildren().equals(2))
        .collect(Collectors.toList()));
  }

  @Test
  void givenFilterWithBigDecimalEqualsCondition_whenFilterAll_thenReturnOnlyFilteredElements() {
    Query query = Querity.query()
        .filter(filterBy("height", EQUALS, new BigDecimal("1.72")))
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize(1);
    assertThat(result).isEqualTo(entities.stream()
        .filter(p -> p.getHeight().compareTo(new BigDecimal("1.72")) == 0)
        .collect(Collectors.toList()));
  }

  @Test
  void givenFilterWithBigDecimalEqualsConditionAsString_whenFilterAll_thenReturnOnlyFilteredElements() {
    Query query = Querity.query()
        .filter(filterBy("height", EQUALS, "1.72"))
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize(1);
    assertThat(result).isEqualTo(entities.stream()
        .filter(p -> p.getHeight().compareTo(new BigDecimal("1.72")) == 0)
        .collect(Collectors.toList()));
  }

  @Test
  void givenFilterWithBigDecimalGreaterThanCondition_whenFilterAll_thenReturnOnlyFilteredElements() {
    Query query = Querity.query()
        .filter(filterBy("height", GREATER_THAN, "1.72"))
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize(4);
    assertThat(result).isEqualTo(entities.stream()
        .filter(p -> p.getHeight().compareTo(new BigDecimal("1.72")) > 0)
        .collect(Collectors.toList()));
  }

  @Test
  void givenFilterWithBigDecimalGreaterThanEqualsCondition_whenFilterAll_thenReturnOnlyFilteredElements() {
    Query query = Querity.query()
        .filter(filterBy("height", GREATER_THAN_EQUALS, "1.72"))
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize(5);
    assertThat(result).isEqualTo(entities.stream()
        .filter(p -> p.getHeight().compareTo(new BigDecimal("1.72")) >= 0)
        .collect(Collectors.toList()));
  }

  @Test
  void givenFilterWithBigDecimalLesserThanCondition_whenFilterAll_thenReturnOnlyFilteredElements() {
    Query query = Querity.query()
        .filter(filterBy("height", LESSER_THAN, "1.72"))
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize(1);
    assertThat(result).isEqualTo(entities.stream()
        .filter(p -> p.getHeight().compareTo(new BigDecimal("1.72")) < 0)
        .collect(Collectors.toList()));
  }

  @Test
  void givenFilterWithBigDecimalLesserThanEqualsCondition_whenFilterAll_thenReturnOnlyFilteredElements() {
    Query query = Querity.query()
        .filter(filterBy("height", LESSER_THAN_EQUALS, "1.72"))
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize(2);
    assertThat(result).isEqualTo(entities.stream()
        .filter(p -> p.getHeight().compareTo(new BigDecimal("1.72")) <= 0)
        .collect(Collectors.toList()));
  }

  @Test
  void givenFilterWithStringNotEqualsCondition_whenFilterAll_thenReturnOnlyFilteredElements() {
    Query query = Querity.query()
        .filter(filterBy("lastName", NOT_EQUALS, "Skywalker"))
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize(4);
    assertThat(result).isEqualTo(entities.stream().filter(p -> !"Skywalker".equals(p.getLastName())).collect(Collectors.toList()));
  }

  @Test
  void givenFilterWithStringStartsWithCondition_whenFilterAll_thenReturnOnlyFilteredElements() {
    Query query = Querity.query()
        .filter(filterBy("lastName", STARTS_WITH, "Sky"))
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize(2);
    assertThat(result).isEqualTo(entities.stream().filter(p -> p.getLastName() != null && p.getLastName().startsWith("Sky")).collect(Collectors.toList()));
  }

  @Test
  void givenFilterWithStringEndsWithCondition_whenFilterAll_thenReturnOnlyFilteredElements() {
    Query query = Querity.query()
        .filter(filterBy("lastName", ENDS_WITH, "walker"))
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize(2);
    assertThat(result).isEqualTo(entities.stream().filter(p -> p.getLastName() != null && p.getLastName().endsWith("walker")).collect(Collectors.toList()));
  }

  @Test
  void givenFilterWithStringContainsCondition_whenFilterAll_thenReturnOnlyFilteredElements() {
    Query query = Querity.query()
        .filter(filterBy("lastName", CONTAINS, "walk"))
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize(2);
    assertThat(result).isEqualTo(entities.stream()
        .filter(p -> p.getLastName() != null && p.getLastName().contains("walk"))
        .collect(Collectors.toList()));
  }

  @Test
  void givenFilterWithStringNotContainsCondition_whenFilterAll_thenReturnOnlyFilteredElements() {
    Query query = Querity.query()
        .filter(not(filterBy("lastName", CONTAINS, "walk")))
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize(4);
    assertThat(result).isEqualTo(entities.stream()
        .filter(p -> p.getLastName() == null || !p.getLastName().contains("walk"))
        .collect(Collectors.toList()));
  }

  @Test
  void givenFilterWithStringIsNullCondition_whenFilterAll_thenReturnOnlyFilteredElements() {
    Query query = Querity.query()
        .filter(filterBy("lastName", IS_NULL))
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize(1);
    assertThat(result).isEqualTo(entities.stream().filter(p -> p.getLastName() == null).collect(Collectors.toList()));
  }

  @Test
  void givenFilterWithStringIsNotNullCondition_whenFilterAll_thenReturnOnlyFilteredElements() {
    Query query = Querity.query()
        .filter(filterBy("lastName", IS_NOT_NULL))
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize(5);
    assertThat(result).isEqualTo(entities.stream().filter(p -> p.getLastName() != null).collect(Collectors.toList()));
  }

  @Test
  void givenFilterWithTwoStringEqualsConditionsWithAndLogic_whenFilterAll_thenReturnOnlyFilteredElements() {
    Query query = Querity.query()
        .filter(and(
            filterBy("lastName", EQUALS, "Skywalker"),
            filterBy("firstName", EQUALS, "Luke")
        ))
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize(1);
    assertThat(result).isEqualTo(entities.stream()
        .filter(p -> "Skywalker".equals(p.getLastName()) && "Luke".equals(p.getFirstName()))
        .collect(Collectors.toList()));
  }

  @Test
  void givenFilterWithTwoStringEqualsConditionsWithOrLogic_whenFilterAll_thenReturnOnlyFilteredElements() {
    Query query = Querity.query()
        .filter(or(
            filterBy("lastName", EQUALS, "Skywalker"),
            filterBy("lastName", EQUALS, "Kenobi")
        ))
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize(3);
    assertThat(result).isEqualTo(entities.stream()
        .filter(p -> "Skywalker".equals(p.getLastName()) || "Kenobi".equals(p.getLastName()))
        .collect(Collectors.toList()));
  }

  @Test
  void givenFilterWithNestedConditions_whenFindAll_thenReturnListOfEntity() {
    Query query = Querity.query()
        .filter(and(
            filterBy("lastName", EQUALS, "Skywalker"),
            or(
                filterBy("firstName", EQUALS, "Anakin"),
                filterBy("firstName", EQUALS, "Luke")
            ))
        )
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize(2);
    assertThat(result).isEqualTo(entities.stream()
        .filter(p -> "Skywalker".equals(p.getLastName()) && ("Anakin".equals(p.getFirstName()) || "Luke".equals(p.getFirstName())))
        .collect(Collectors.toList()));
  }

  @Test
  void givenFilterWithStringEqualsConditionOnNestedField_whenFilterAll_thenReturnOnlyFilteredElements() {
    Query query = Querity.query()
        .filter(filterBy("address.city", EQUALS, "Tatooine"))
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize(1);
    assertThat(result).isEqualTo(entities.stream().filter(p -> "Tatooine".equals(p.getAddress().getCity())).collect(Collectors.toList()));
  }

  @Test
  void givenPagination_whenFilterAll_thenReturnThePageElements() {
    Query query = Querity.query()
        .pagination(2, 3)
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize(3);
    assertThat(result).isEqualTo(entities.stream().skip(3).limit(3).collect(Collectors.toList()));
  }

  @Test
  void givenSortByFieldAscending_whenFilterAll_thenReturnSortedElements() {
    Query query = Querity.query()
        .sort(sortBy("lastName"))
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    Comparator<T> comparator = Comparator
        .comparing((T p) -> p.getLastName(), getSortComparator());
    assertThat(result).hasSize(6);
    assertThat(result).isEqualTo(entities.stream().sorted(comparator).collect(Collectors.toList()));
  }

  @Test
  void givenSortByFieldDescending_whenFilterAll_thenReturnSortedElements() {
    Query query = Querity.query()
        .sort(sortBy("lastName", DESC))
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    Comparator<T> comparator = Comparator
        .comparing((T p) -> p.getLastName(), getSortComparator()).reversed();
    assertThat(result).hasSize(6);
    assertThat(result).isEqualTo(entities.stream().sorted(comparator).collect(Collectors.toList()));
  }

  @Test
  void givenSortByMultipleFields_whenFilterAll_thenReturnSortedElements() {
    Query query = Querity.query()
        .sort(sortBy("lastName"), sortBy("firstName"))
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    Comparator<T> comparator = Comparator
        .comparing((T p) -> p.getLastName(), getSortComparator())
        .thenComparing((T p) -> p.getFirstName());
    assertThat(result).hasSize(6);
    assertThat(result).isEqualTo(entities.stream().sorted(comparator).collect(Collectors.toList()));
  }

  /**
   * Override this method if the database doesn't support handling null values in sorting
   */
  protected <C extends Comparable<? super C>> Comparator<C> getSortComparator() {
    return Comparator.nullsLast(Comparator.naturalOrder());
  }

  @Test
  void givenFilterWithNotConditionWithStringEqualsCondition_whenFilterAll_thenReturnOnlyFilteredElements() {
    Query query = Querity.query()
        .filter(not(filterBy("lastName", EQUALS, "Skywalker")))
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize(4);
    assertThat(result).isEqualTo(entities.stream().filter(p -> !("Skywalker".equals(p.getLastName()))).collect(Collectors.toList()));
  }

  @Test
  void givenFilterWithTwoNestedNotConditionsWithStringEqualsCondition_whenFilterAll_thenReturnOnlyFilteredElements() {
    Query query = Querity.query()
        .filter(not(not(filterBy("lastName", EQUALS, "Skywalker"))))
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize(2);
    assertThat(result).isEqualTo(entities.stream().filter(p -> "Skywalker".equals(p.getLastName())).collect(Collectors.toList()));
  }

  @Test
  void givenFilterWithNotConditionWithTwoStringEqualsConditionsWithAndLogic_whenFilterAll_thenReturnOnlyFilteredElements() {
    Query query = Querity.query()
        .filter(not(and(
            filterBy("lastName", EQUALS, "Skywalker"),
            filterBy("firstName", EQUALS, "Luke")
        )))
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize(5);
    assertThat(result).isEqualTo(entities.stream()
        .filter(p -> !("Skywalker".equals(p.getLastName()) && "Luke".equals(p.getFirstName())))
        .collect(Collectors.toList()));
  }

  @Test
  void givenFilterWithNotConditionWithTwoStringEqualsConditionsWithOrLogic_whenFilterAll_thenReturnOnlyFilteredElements() {
    Query query = Querity.query()
        .filter(not(or(
            filterBy("lastName", EQUALS, "Skywalker"),
            filterBy("lastName", EQUALS, "Kenobi")
        )))
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize(3);
    assertThat(result).isEqualTo(entities.stream()
        .filter(p -> !("Skywalker".equals(p.getLastName()) || "Kenobi".equals(p.getLastName())))
        .collect(Collectors.toList()));
  }

  @Test
  void givenFilterWithNotConditionWithNestedConditions_whenFindAll_thenReturnListOfEntity() {
    Query query = Querity.query()
        .filter(not(and(
            filterBy("lastName", EQUALS, "Skywalker"),
            or(
                filterBy("firstName", EQUALS, "Anakin"),
                filterBy("firstName", EQUALS, "Luke")
            ))
        ))
        .build();
    List<T> result = querity.findAll(getEntityClass(), query);
    assertThat(result).hasSize(4);
    assertThat(result).isEqualTo(entities.stream()
        .filter(p -> !("Skywalker".equals(p.getLastName()) && ("Anakin".equals(p.getFirstName()) || "Luke".equals(p.getFirstName()))))
        .collect(Collectors.toList()));
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
