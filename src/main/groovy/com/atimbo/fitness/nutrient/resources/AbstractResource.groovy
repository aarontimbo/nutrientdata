package com.atimbo.fitness.nutrient.resources

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.sun.jersey.api.NotFoundException
import groovy.transform.CompileStatic

/**
 * Base dropwizard resource class. All resources should extend this.
 */
@CompileStatic
abstract class AbstractResource {

    protected final ObjectMapper objectMapper

    AbstractResource(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper
    }

    public <T> T translateAndReturn(Object obj, Class<T> klass, String errorMessage = null) {
        verifyNotNull(obj)
        return convert(obj, klass)
    }

    public void verifyNotNull(Object obj, String errorMessage = null) {
        if (obj == null) {
            String msg = errorMessage ?: 'Not found'
            throw new NotFoundException(msg)
        }
    }

    protected  <T> T convert(Object obj, Class<T> klass) {
        return objectMapper.convertValue(obj, klass)
    }

    protected  <T> T convert(Object obj, TypeReference<T> ref) {
        return objectMapper.convertValue(obj, ref)
    }

}
