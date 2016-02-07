package com.github.fit.examples;


import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class JsonContextResolver implements ContextResolver<ObjectMapper> {
    private final static ObjectMapper OBJECT_MAPPER;
    static {
        OBJECT_MAPPER = new ObjectMapper();
        // todo: configure mapper
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return OBJECT_MAPPER;
    }
}
