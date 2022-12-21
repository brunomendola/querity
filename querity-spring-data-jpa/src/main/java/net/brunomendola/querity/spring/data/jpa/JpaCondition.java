package net.brunomendola.querity.spring.data.jpa;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import net.brunomendola.querity.api.Condition;

import java.util.Set;

import static net.brunomendola.querity.common.util.ConditionUtils.getConditionImplementation;
import static net.brunomendola.querity.common.util.ReflectionUtils.findSubclasses;

abstract class JpaCondition {

  public abstract <T> Predicate toPredicate(Class<T> entityClass, Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb);

  private static final Set<Class<? extends JpaCondition>> JPA_CONDITION_IMPLEMENTATIONS = findSubclasses(JpaCondition.class);

  public static JpaCondition of(Condition condition) {
    return getConditionImplementation(JPA_CONDITION_IMPLEMENTATIONS, condition)
        .orElseThrow(() -> new IllegalArgumentException(
            String.format("Condition class %s is not supported by the JPA module", condition.getClass().getSimpleName())));
  }
}
