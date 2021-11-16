package net.brunomendola.querity.spring.data.mongodb;

import lombok.experimental.Delegate;
import net.brunomendola.querity.api.Sort;

public class MongodbSort {
  @Delegate
  private final Sort sort;

  public MongodbSort(Sort sort) {
    this.sort = sort;
  }

  public org.springframework.data.domain.Sort.Order toMongoSortOrder() {
    return new org.springframework.data.domain.Sort.Order(
        getDirection().equals(Sort.Direction.ASC) ?
            org.springframework.data.domain.Sort.Direction.ASC :
            org.springframework.data.domain.Sort.Direction.DESC,
        getPropertyName()
    );
  }
}
