/*
 * Copyright (c) 2017 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package jackson.jsonnode;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.text.StrBuilder;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A sample builder to create a JsonNode.
 *
 * @author Rick Rupp
 */
@SuppressWarnings({"JavaDoc", "WeakerAccess"})
public class JsonNodeBuilder {

    private final ObjectMapper objectMapper;
    private final Stack<JsonNode> currentJsonNode;
    private final JsonNode jsonNode;
    private final Supplier<ObjectNode> objectNode;
    private final Supplier<ArrayNode> arrayNode;
    private final Function<Object, ObjectNode> pojoToObjectNode;

    private JsonNodeBuilder(final ObjectMapper objectMapper, final JsonNode jsonNode) {
        this.objectMapper = Objects.requireNonNull(objectMapper, "The Jackson ObjectMapper cannot be null...");
        this.jsonNode = Objects.requireNonNull(jsonNode, "The root JsonNode cannot be null...");
        currentJsonNode = new Stack<>();
        currentJsonNode.push(jsonNode);
        objectNode = () -> {
            if (!isObject()) {
                throw new IllegalStateException("Current JsonNode is not an object");
            }
            return (ObjectNode) currentJsonNode.peek();
        };
        arrayNode = () -> {
            if (!isArray()) {
                throw new IllegalStateException("Current JsonNode is not an array");
            }
            return (ArrayNode) currentJsonNode.peek();
        };
        pojoToObjectNode = (value) -> {
            if (value instanceof Collection<?> || value instanceof Map<?,?>) {
                throw new IllegalArgumentException("value must be a POJO not a Collection or Map...");
            }
            return objectMapper.convertValue(value, ObjectNode.class);
        };
    }

    public static JsonNodeBuilder createJsonObject(final ObjectMapper objectMapper) {
        return new JsonNodeBuilder(objectMapper, JsonNodeFactory.instance.objectNode());
    }

    public static JsonNodeBuilder createJsonArray(final ObjectMapper objectMapper) {
        return new JsonNodeBuilder(objectMapper, JsonNodeFactory.instance.arrayNode());
    }

    public boolean isObject() {
        return currentJsonNode.peek().isObject();
    }

    public boolean isArray() {
        return currentJsonNode.peek().isArray();
    }

    public JsonNode get() {
        return jsonNode;
    }

    public JsonNodeBuilder put(final String fieldName, final String value) {
        objectNode.get().put(fieldName, value);
        return this;
    }

    public JsonNodeBuilder put(final String fieldName, final Integer value) {
        objectNode.get().put(fieldName, value);
        return this;
    }

    public JsonNodeBuilder put(final String fieldName, final Long value) {
        objectNode.get().put(fieldName, value);
        return this;
    }

    public JsonNodeBuilder put(final String fieldName, final Short value) {
        objectNode.get().put(fieldName, value);
        return this;
    }

    public JsonNodeBuilder put(final String fieldName, final Double value) {
        objectNode.get().put(fieldName, value);
        return this;
    }

    public JsonNodeBuilder put(final String fieldName, final BigDecimal value) {
        objectNode.get().put(fieldName, value);
        return this;
    }

    public JsonNodeBuilder put(final String fieldName, final Boolean value) {
        objectNode.get().put(fieldName, value);
        return this;
    }

    public JsonNodeBuilder putPojo(final String fieldName, final Object value) {
        if (Objects.nonNull(value)) {
            objectNode.get().putObject(fieldName).setAll(pojoToObjectNode.apply(value));
        }
        return this;
    }

    public JsonNodeBuilder pushObject(final String fieldName) {
        currentJsonNode.push(objectNode.get().putObject(fieldName));
        return this;
    }

    public JsonNodeBuilder pushArray(final String fieldName) {
        currentJsonNode.push(objectNode.get().withArray(fieldName));
        return this;
    }

    public JsonNodeBuilder pop() {
        if (currentJsonNode.size() < 2) {
            throw new IllegalStateException("Cannot pop the root JsonObject");
        }
        currentJsonNode.pop();
        return this;
    }

    public JsonNodeBuilder add(final String value) {
        arrayNode.get().add(value);
        return this;
    }

    public JsonNodeBuilder add(final Short value) {
        arrayNode.get().add(value);
        return this;
    }

    public JsonNodeBuilder add(final Integer value) {
        arrayNode.get().add(value);
        return this;
    }

    public JsonNodeBuilder add(final Long value) {
        arrayNode.get().add(value);
        return this;
    }

    public JsonNodeBuilder add(final BigDecimal value) {
        arrayNode.get().add(value);
        return this;
    }

    public JsonNodeBuilder add(final Double value) {
        arrayNode.get().add(value);
        return this;
    }

    public JsonNodeBuilder add(final Boolean value) {
        arrayNode.get().add(value);
        return this;
    }

    public JsonNodeBuilder addPojo(final Object value) {
        if (Objects.nonNull(value)) {
            if (value instanceof Collection<?> || value instanceof Map<?,?>) {
                throw new IllegalArgumentException("value must be a POJO not a Collection or Map...");
            }
            final ObjectNode objectNode = arrayNode.get().addObject();
            objectNode.setAll(objectMapper.convertValue(value, ObjectNode.class));
        }
        return this;
    }

    public String toPrettyString() {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        } catch (final Exception error) {
            return new StrBuilder().append('{')
                                   .append('"').append("error").append('"').append(':')
                                   .append('"').append(error.getMessage()).append('"')
                                   .append('}')
                                   .build();
        }
    }

    @Override
    public String toString() {
        return jsonNode.toString();
    }
}
