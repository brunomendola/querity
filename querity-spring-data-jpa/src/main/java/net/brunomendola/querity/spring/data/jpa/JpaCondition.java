package net.brunomendola.querity.spring.data.jpa;

import net.brunomendola.querity.api.Condition;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Set;

import static net.brunomendola.querity.common.util.ConditionUtils.getConditionImplementation;
import static net.brunomendola.querity.common.util.ReflectionUtils.findSubclasses;

abstract class JpaCondition {

  public abstract <T> Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb);

  private static final Set<Class<? extends JpaCondition>> JPA_CONDITION_IMPLEMENTATIONS = findSubclasses(JpaCondition.class);

  public static JpaCondition of(Condition condition) {
    return getConditionImplementation(JPA_CONDITION_IMPLEMENTATIONS, condition)
        .orElseThrow(() -> new IllegalArgumentException(
            String.format("Condition class %s is not supported by the JPA module", condition.getClass().getSimpleName())));
  }
}
