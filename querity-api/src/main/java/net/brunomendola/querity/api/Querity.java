package net.brunomendola.querity.api;

import java.util.List;

public interface Querity {
  <T> List<T> findAll(Class<T> entityClass, Query query);
}
