package net.brunomendola.querity.api;

import lombok.extern.slf4j.Slf4j;
import net.brunomendola.querity.api.domain.Person;

import java.util.Collections;
import java.util.List;

@Slf4j
public class QuerityDummyImpl implements Querity {
  @Override
  public <T> List<T> findAll(Class<T> entityClass, Query query) {
    if (entityClass.equals(Person.class)) {
      log.warn("Received filter {} but no filter is applied in this dummy implementation.", query.getFilter());
      return Collections.emptyList();
    } else throw new IllegalArgumentException("Unsupported entity");
  }
}
