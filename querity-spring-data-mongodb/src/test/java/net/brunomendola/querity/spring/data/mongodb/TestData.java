package net.brunomendola.querity.spring.data.mongodb;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.brunomendola.querity.spring.data.mongodb.domain.Person;
import net.brunomendola.querity.test.domain.Pet;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestData {
  static List<Person> getPeople() {
    return Arrays.asList(
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
            .build());
  }
}
