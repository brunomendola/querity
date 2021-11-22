package net.brunomendola.querity.api;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
public class QuerityDummyImpl implements Querity {
  @Override
  public <T> List<T> findAll(Class<T> entityClass, Query query) {
    return Collections.emptyList();
  }

  @Override
  public <T> Long count(Class<T> entityClass, Condition condition) {
    return 0L;
  }
}
