package net.brunomendola.querity.spring.data.jpa;

import lombok.experimental.Delegate;
import net.brunomendola.querity.api.Sort;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

class JpaSort {
  @Delegate
  private final Sort sort;

  public JpaSort(Sort sort) {
    this.sort = sort;
  }

  public <T> Order toOrder(Root<T> root, CriteriaBuilder cb) {
    Path<?> propertyPath = JpaPropertyUtils.getPath(root, getPropertyName());
    return getDirection().equals(Sort.Direction.ASC) ?
        cb.asc(propertyPath) :
        cb.desc(propertyPath);
  }
}
