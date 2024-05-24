package com.example.entityrelay;

import java.util.Objects;

public final class PersonId {

    private final long id;

    public PersonId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonId personId = (PersonId) o;
        return id == personId.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public static PersonId randomId() {
        return new PersonId((long) (Math.random() * Long.MAX_VALUE));
    }
}
