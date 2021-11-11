package net.brunomendola.querity.api.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@Builder
public class Person {
  @NonNull
  private String firstName;
  @NonNull
  private String lastName;
  @NonNull
  private LocalDate birthDate;
  @NonNull
  private Integer children;
  @NonNull
  private Pet favouritePet;
}
