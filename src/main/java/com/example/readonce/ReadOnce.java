package com.example.readonce;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.requireNonNull;

public final class ReadOnce<T> implements Externalizable {

    private final AtomicReference<T> value;

    public ReadOnce(T value) {
        this.value = new AtomicReference<>(requireNonNull(value));
    }

    public T value() {
        var returnValue = value.getAndSet(null);
        if (returnValue == null) {
            throw new IllegalStateException("Value has already been consumed");
        }
        return returnValue;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        throw new UnsupportedOperationException("Serialization of read once values is not allowed");
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException("Deserialization of read once values is not allowed");
    }
}
