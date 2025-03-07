package net.brunomendola.querity.spring.data.elasticsearch;

import lombok.experimental.Delegate;
import net.brunomendola.querity.api.NativeConditionWrapper;
import org.springframework.data.elasticsearch.core.query.Criteria;

class ElasticsearchNativeConditionWrapper extends ElasticsearchCondition {
  @Delegate
  private final NativeConditionWrapper<Criteria> nativeConditionWrapper;

  ElasticsearchNativeConditionWrapper(NativeConditionWrapper<Criteria> nativeConditionWrapper) {
    this.nativeConditionWrapper = nativeConditionWrapper;
  }

  @Override
  public <T> Criteria toCriteria(Class<T> entityClass, boolean negate) {
    if (negate)
      throw new IllegalArgumentException("Not conditions wrapping native conditions is not supported; just write a negative native condition.");
    return getNativeCondition();
  }
}
