package net.brunomendola.querity.spring.data.jpa.domain;

import lombok.*;
import net.brunomendola.querity.test.domain.Pet;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person extends AbstractPersistable<Long> implements net.brunomendola.querity.test.domain.Person<Address> {
  @NonNull
  private String firstName;
  private String lastName;
  @NonNull
  private LocalDate birthDate;
  @NonNull
  private BigDecimal height;
  @NonNull
  private Integer children;
  @Enumerated(EnumType.STRING)
  private Pet favouritePet;
  @OneToOne(cascade = CascadeType.ALL)
  @NonNull
  private Address address;
}
