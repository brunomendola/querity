package net.brunomendola.querity.spring.data.jpa;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.experimental.Delegate;
import net.brunomendola.querity.api.NativeConditionWrapper;
import org.springframework.data.jpa.domain.Specification;

class JpaNativeConditionWrapper extends JpaCondition {
  @Delegate
  private final NativeConditionWrapper<Specification<?>> nativeConditionWrapper;

  JpaNativeConditionWrapper(NativeConditionWrapper<Specification<?>> nativeConditionWrapper) {
    this.nativeConditionWrapper = nativeConditionWrapper;
  }

  @Override
  @SuppressWarnings({"unchecked", "rawtypes"})
  public <T> Predicate toPredicate(Class<T> entityClass, Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    return getNativeCondition().toPredicate((Root) root, cq, cb);
  }
}
