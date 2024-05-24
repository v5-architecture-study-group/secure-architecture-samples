package com.example.domainprimitives.security;

import java.io.Serializable;
import java.util.Objects;

public final class Username implements Serializable {

    private final String value;

    public Username(String value) {
        this.value = validate(value);
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return "Username[%s]".formatted(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Username username = (Username) o;
        return Objects.equals(value, username.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    public static String validate(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Username must not be null");
        }
        if (value.isBlank()) {
            throw new IllegalArgumentException("Username must not be blank");
        }
        if (value.length() < 3) {
            throw new IllegalArgumentException("Username must be at least 3 characters long");
        }
        if (value.length() > 32) {
            throw new IllegalArgumentException("Username must be at most 32 characters long");
        }
        if (!value.matches("[a-zA-Z0-9]+")) {
            throw new IllegalArgumentException("Username must contain only letters and digits");
        }
        return value;
    }
}
