package com.flamingo.qa.utils;

import lombok.experimental.UtilityClass;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.json.JsonMapper;

@UtilityClass
public class JsonUtils {

    private static final JsonMapper MAPPER = JsonMapper.builder()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .build();

    public static String serialize(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JacksonException e) {
            throw new IllegalArgumentException("Failed to serialize object to JSON", e);
        }
    }

    public static <T> T deserialize(String json, Class<T> type) {
        try {
            return MAPPER.readValue(json, type);
        } catch (JacksonException e) {
            throw new IllegalArgumentException("Failed to deserialize JSON to " + type.getSimpleName(), e);
        }
    }

    public static JsonMapper getMapper() {
        return MAPPER;
    }
}
