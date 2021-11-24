package net.brunomendola.querity.spring.data.jpa.domain;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
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
  @OneToOne(cascade = CascadeType.ALL)
  @NonNull
  private Address address;
  private boolean jediMaster;
}
