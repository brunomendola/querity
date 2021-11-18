package net.brunomendola.querity.spring.data.mongodb.domain;

import lombok.*;
import net.brunomendola.querity.test.domain.Pet;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person implements net.brunomendola.querity.test.domain.Person<Address> {
  @Id
  private String id;
  @NonNull
  private String firstName;
  private String lastName;
  @NonNull
  private LocalDate birthDate;
  @NonNull
  private Integer children;
  private Pet favouritePet;
  @NonNull
  private Address address;
}
