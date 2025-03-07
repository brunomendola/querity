package net.brunomendola.querity.spring.data.elasticsearch.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location implements net.brunomendola.querity.test.domain.Location {
  @NonNull
  private String country;
  @NonNull
  @Builder.Default
  private List<String> cities = new ArrayList<>();

  @Override
  public @NonNull String toString() {
    return "Location{" +
           "country='" + country + '\'' +
           '}';
  }
}
