package net.brunomendola.querity.spring.data.mongodb.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location implements net.brunomendola.querity.test.domain.Location {
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
