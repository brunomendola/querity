package net.brunomendola.querity.spring.data.jpa.domain;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

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
  @NonNull
  private String country;
  @NonNull
  @ElementCollection
  @Builder.Default
  private List<String> cities = new ArrayList<>();

  @Override
  public @NonNull String toString() {
    return "Location{" +
        "country='" + country + '\'' +
        "} " + super.toString();
  }
}
