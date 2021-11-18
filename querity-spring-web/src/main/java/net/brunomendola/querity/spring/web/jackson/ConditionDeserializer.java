package net.brunomendola.querity.spring.web.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.SneakyThrows;
import net.brunomendola.querity.api.*;

import java.io.IOException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ConditionDeserializer extends StdDeserializer<Condition> {

  public static final String FIELD_CONDITIONS_WRAPPER_LOGIC = "logic";
  public static final String FIELD_CONDITIONS_WRAPPER_CONDITIONS = "conditions";
  public static final String FIELD_SIMPLE_CONDITION_PROPERTY_NAME = "propertyName";
  public static final String FIELD_SIMPLE_CONDITION_OPERATOR = "operator";
  public static final String FIELD_SIMPLE_CONDITION_VALUE = "value";

  protected ConditionDeserializer(JavaType valueType) {
    super(valueType);
  }

  @Override
  public Condition deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
    JsonNode root = jsonParser.readValueAsTree();
    return parseCondition(root, jsonParser);
  }

  @SneakyThrows
  private static Condition parseCondition(JsonNode jsonNode, JsonParser jsonParser) {
    JsonParser jp = jsonNode.traverse();
    jp.setCodec(jsonParser.getCodec());
    return isConditionsWrapper(jsonNode) ?
        parseConditionsWrapper(jsonNode, jsonParser) :
        parseSimpleCondition(jsonNode);
  }

  private static boolean isConditionsWrapper(JsonNode jsonNode) {
    return jsonNode.hasNonNull(FIELD_CONDITIONS_WRAPPER_LOGIC);
  }

  private static ConditionsWrapper parseConditionsWrapper(JsonNode jsonNode, JsonParser jsonParser) {
    return ConditionsWrapper.builder()
        .logic(LogicOperator.valueOf(jsonNode.get(FIELD_CONDITIONS_WRAPPER_LOGIC).asText()))
        .conditions(StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(
                    jsonNode.get(FIELD_CONDITIONS_WRAPPER_CONDITIONS).elements(), Spliterator.ORDERED),
                false)
            .map(n -> parseCondition(n, jsonParser))
            .collect(Collectors.toList()))
        .build();
  }

  private static SimpleCondition parseSimpleCondition(JsonNode jsonNode) {
    return SimpleCondition.builder()
        .propertyName(jsonNode.get(FIELD_SIMPLE_CONDITION_PROPERTY_NAME).asText())
        .operator(Operator.valueOf(jsonNode.get(FIELD_SIMPLE_CONDITION_OPERATOR).asText()))
        .value(jsonNode.get(FIELD_SIMPLE_CONDITION_VALUE).asText())
        .build();
  }
}
