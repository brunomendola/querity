package net.brunomendola.querity.api;

import lombok.extern.slf4j.Slf4j;
import net.brunomendola.querity.api.domain.Person;
import net.brunomendola.querity.api.domain.Pet;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class QuerityDummyImpl implements Querity {

  private static final List<Person> PEOPLE = Arrays.asList(
      Person.builder()
          .firstName("Obi-wan")
          .lastName("Kenobi")
          .birthDate(LocalDate.of(0, Month.DECEMBER, 25))
          .children(0)
          .favouritePet(Pet.RABBIT)
          .build(),
      Person.builder()
          .firstName("Anakin")
          .lastName("Skywalker")
          .birthDate(LocalDate.of(1962, Month.JANUARY, 8))
          .children(2)
          .favouritePet(Pet.DOG)
          .build(),
      Person.builder()
          .firstName("Luke")
          .lastName("Skywalker")
          .birthDate(LocalDate.of(1982, Month.JULY, 8))
          .children(0)
          .favouritePet(Pet.DOG)
          .build(),
      Person.builder()
          .firstName("Leia")
          .lastName("Organa")
          .birthDate(LocalDate.of(1982, Month.JULY, 8))
          .children(1)
          .favouritePet(Pet.CAT)
          .build(),
      Person.builder()
          .firstName("Han")
          .lastName("Solo")
          .birthDate(LocalDate.of(1980, Month.FEBRUARY, 1))
          .children(0)
          .favouritePet(Pet.CAT)
          .build()
  );

  @Override
  @SuppressWarnings("unchecked")
  public <T> List<T> findAll(Class<T> entityClass, Query query) {
    if (entityClass.equals(Person.class)) {
      log.warn("Received filter {} but no filter is applied in this dummy implementation.", query.getFilter());
      return (List<T>) Collections.unmodifiableList(PEOPLE);
    } else throw new IllegalArgumentException("Unsupported entity");
  }
}
