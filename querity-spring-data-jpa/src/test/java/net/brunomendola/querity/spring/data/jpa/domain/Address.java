package net.brunomendola.querity.spring.data.jpa.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address extends AbstractPersistable<Long> implements net.brunomendola.querity.test.domain.Address {
  @OneToOne
  @NonNull
  private Person person;
  @NonNull
  private String streetAddress;
  @NonNull
  private String city;

  @Override
  public @NonNull String toString() {
    return "Address{" +
        "streetAddress='" + streetAddress + '\'' +
        ", city='" + city + '\'' +
        '}';
  }
}
