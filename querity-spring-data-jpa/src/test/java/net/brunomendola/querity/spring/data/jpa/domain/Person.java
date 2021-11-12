package net.brunomendola.querity.spring.data.jpa.domain;

import lombok.*;
import net.brunomendola.querity.test.domain.Pet;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person extends AbstractPersistable<Long> implements net.brunomendola.querity.test.domain.Person {
  @NonNull
  private String firstName;
  @NonNull
  private String lastName;
  @NonNull
  private LocalDate birthDate;
  @NonNull
  private Integer children;
  @Enumerated(EnumType.STRING)
  @NonNull
  private Pet favouritePet;
}
