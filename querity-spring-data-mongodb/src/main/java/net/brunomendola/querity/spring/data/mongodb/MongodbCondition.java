package net.brunomendola.querity.spring.data.mongodb;

import net.brunomendola.querity.api.Condition;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Set;

import static net.brunomendola.querity.common.util.ConditionUtils.findSubclasses;
import static net.brunomendola.querity.common.util.ConditionUtils.getConditionImplementation;

abstract class MongodbCondition {

  public Criteria toCriteria() {
    return toCriteria(false);
  }

  public abstract Criteria toCriteria(boolean negate);

  private static final Set<Class<? extends MongodbCondition>> MONGODB_CONDITION_IMPLEMENTATIONS = findSubclasses(MongodbCondition.class);

  public static MongodbCondition of(Condition condition) {
    return getConditionImplementation(MONGODB_CONDITION_IMPLEMENTATIONS, condition)
        .orElseThrow(() -> new IllegalArgumentException(
            String.format("Condition class %s is not supported by the MongoDB module", condition.getClass().getSimpleName())));
  }
}
