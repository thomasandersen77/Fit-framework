package com.github.fit.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    static ObjectMapper om = new ObjectMapper();

    public static <T> String toJson(T type, boolean pretty) {
        try {
            if(pretty) {
                return om.writerWithDefaultPrettyPrinter().writeValueAsString(type);
            } else {
                return om.writeValueAsString(type);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
