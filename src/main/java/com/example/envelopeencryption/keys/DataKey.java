package com.example.envelopeencryption.keys;

import com.example.envelopeencryption.log.EncryptionLoggerFactory;

import javax.crypto.SecretKey;
import javax.security.auth.DestroyFailedException;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public final class DataKey implements Externalizable {

    private final DataKeyIdentifier identifier;
    private final AtomicReference<SecretKey> key;

    public DataKey(DataKeyIdentifier identifier, SecretKey key) {
        this.identifier = requireNonNull(identifier);
        this.key = new AtomicReference<>(requireNonNull(key));
    }

    public DataKeyIdentifier identifier() {
        return identifier;
    }

    public <R> R doWithKey(Function<SecretKey, R> function) {
        var key = this.key.getAndSet(null);
        if (key == null || key.isDestroyed()) {
            throw new IllegalStateException("Key has been destroyed");
        }
        try {
            return function.apply(key);
        } finally {
            EncryptionLoggerFactory.getInstance().dataKeyConsumed(this);
            destroy();
        }
    }

    public void destroy() {
        var key = this.key.getAndSet(null);
        if (key != null) {
            try {
                key.destroy();
            } catch (DestroyFailedException ex) {
                EncryptionLoggerFactory.getInstance().dataKeyDestroyFailed(this, ex);
            }
        }
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        throw new UnsupportedOperationException("Serialization of data keys is not allowed");
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException("Deserialization of data keys is not allowed");
    }
}
