package com.example.readonce;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;

public final class Password implements Externalizable {

    private final char[] value;
    private boolean consumed = false;

    public Password(final char[] value) {
        this.value = validate(value).clone();
    }

    private static char[] validate(final char[] password) {
        if (password.length < 12) {
            throw new IllegalArgumentException("Password must be at least 12 characters long");
        }
        if (password.length > 128) {
            throw new IllegalArgumentException("Password must be at most 128 characters long");
        }
        return password;
    }

    public synchronized char[] value() {
        if (consumed) {
            throw new IllegalStateException("Password has already been consumed");
        }
        final char[] returnValue = value.clone();
        consumed = true;
        Arrays.fill(value, '0');
        return returnValue;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        throw new UnsupportedOperationException("Serialization of passwords is not allowed");
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException("Deserialization of passwords is not allowed");
    }
}
