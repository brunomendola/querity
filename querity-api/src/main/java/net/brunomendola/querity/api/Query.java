package net.brunomendola.querity.api;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Query {
  private Filter filter;

  // Lombok builder customization
  public static class QueryBuilder {
    private Filter filter = new Filter();

    public QueryBuilder addFilterCondition(String propertyName, Operator operator, String value) {
      this.filter.getConditions().add(Condition.builder()
          .propertyName(propertyName)
          .operator(operator)
          .value(value)
          .build());
      return this;
    }

    public QueryBuilder setFilterLogic(LogicOperator logicOperator) {
      this.filter.setLogicOperator(logicOperator);
      return this;
    }
  }
}
