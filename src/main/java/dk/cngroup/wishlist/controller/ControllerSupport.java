package dk.cngroup.wishlist.controller;

import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.function.Consumer;

public final class ControllerSupport {
    private ControllerSupport() {
    }

    public static EmptyResultDataAccessException resourceNotFound(String resource, Long id) {
        return new EmptyResultDataAccessException("No " + resource + " found for id " + id, 1);
    }

    public static EmptyResultDataAccessException resourceNotFound(String resource, String lookup) {
        return new EmptyResultDataAccessException("No " + resource + " found for " + lookup, 1);
    }

    public static <T> void ifDefined(JsonNullable<T> value, Consumer<T> block) {
        if (value != null && value.isPresent()) {
            block.accept(value.get());
        }
    }
}
