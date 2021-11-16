package net.brunomendola.querity.spring.data.jpa.domain;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address extends AbstractPersistable<Long> implements net.brunomendola.querity.test.domain.Address {
  @OneToOne(mappedBy = "address")
  private Person person;
  @NonNull
  private String city;
}
