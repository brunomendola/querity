package net.brunomendola.querity.spring.data.mongodb;

import net.brunomendola.querity.api.Condition;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Set;

import static net.brunomendola.querity.common.util.ConditionUtils.getConditionImplementation;
import static net.brunomendola.querity.common.util.ReflectionUtils.findSubclasses;

abstract class MongodbCondition {

  public <T> Criteria toCriteria(Class<T> entityClass) {
    return toCriteria(entityClass, false);
  }

  public abstract <T> Criteria toCriteria(Class<T> entityClass, boolean negate);

  private static final Set<Class<? extends MongodbCondition>> MONGODB_CONDITION_IMPLEMENTATIONS = findSubclasses(MongodbCondition.class);

  public static MongodbCondition of(Condition condition) {
    return getConditionImplementation(MONGODB_CONDITION_IMPLEMENTATIONS, condition)
        .orElseThrow(() -> new IllegalArgumentException(
            String.format("Condition class %s is not supported by the MongoDB module", condition.getClass().getSimpleName())));
  }
}
