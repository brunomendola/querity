package net.brunomendola.querity.spring.data.jpa;

import lombok.experimental.Delegate;
import net.brunomendola.querity.api.Sort;

import javax.persistence.criteria.*;

class JpaSort {
  @Delegate
  private final Sort sort;

  public JpaSort(Sort sort) {
    this.sort = sort;
  }

  public <T> Order toOrder(Root<T> root, CriteriaQuery<T> cq, CriteriaBuilder cb) {
    Path<?> path = JpaPropertyUtils.getPath(root, getPropertyName());
    return getDirection().equals(Sort.Direction.ASC) ?
        cb.asc(path) :
        cb.desc(path);
  }
}
