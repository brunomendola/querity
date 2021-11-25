package net.brunomendola.querity.spring.data.jpa.domain;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location extends AbstractPersistable<Long> implements net.brunomendola.querity.test.domain.Location {
  @ManyToOne
  @NonNull
  private Person person;
  private String city;
  @NonNull
  private String country;

  @Override
  public @NonNull String toString() {
    return "Location{" +
        "city='" + city + '\'' +
        ", country='" + country + '\'' +
        '}';
  }
}
