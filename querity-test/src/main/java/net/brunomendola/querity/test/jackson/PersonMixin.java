package net.brunomendola.querity.test.jackson;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import net.brunomendola.querity.test.domain.Address;

public interface PersonMixin {
  @JsonUnwrapped(prefix = "address_")
  Address getAddress();
}
