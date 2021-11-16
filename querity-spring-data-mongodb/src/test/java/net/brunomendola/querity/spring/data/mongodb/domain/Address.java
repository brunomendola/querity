package net.brunomendola.querity.spring.data.mongodb.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address implements net.brunomendola.querity.test.domain.Address {
  private String city;
}
