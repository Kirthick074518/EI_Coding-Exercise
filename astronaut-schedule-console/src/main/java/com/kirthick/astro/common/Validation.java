package com.kirthick.astro.common;

import com.kirthick.astro.common.exceptions.ValidationException;

import java.util.Collection;

public final class Validation {
    private Validation() {}

    public static String requireNonBlank(String value, String name) {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException("" + name + " must not be blank");
        }
        return value.trim();
    }

    public static <T> T requireNonNull(T value, String name) {
        if (value == null) {
            throw new ValidationException("" + name + " must not be null");
        }
        return value;
    }

    public static <T> void requireNotEmpty(Collection<T> c, String name) {
        if (c == null || c.isEmpty()) {
            throw new ValidationException("" + name + " must not be empty");
        }
    }

    public static void require(boolean condition, String message) {
        if (!condition) throw new ValidationException(message);
    }
}
