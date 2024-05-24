package com.example.envelopeencryption.keys;

import com.example.envelopeencryption.log.EncryptionLoggerFactory;

import javax.security.auth.DestroyFailedException;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.security.PrivateKey;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public final class PrivateRootKey implements Externalizable, RootKey {

    private final RootKeyIdentifier identifier;
    private final AtomicReference<PrivateKey> privateKey;

    public PrivateRootKey(RootKeyIdentifier identifier, PrivateKey privateKey) {
        this.identifier = requireNonNull(identifier);
        this.privateKey = new AtomicReference<>(requireNonNull(privateKey));
    }

    @Override
    public RootKeyIdentifier identifier() {
        return identifier;
    }

    public <R> R doWithPrivateKey(Function<PrivateKey, R> function) {
        var key = this.privateKey.getAndSet(null);
        if (key == null || key.isDestroyed()) {
            throw new IllegalStateException("Key has been destroyed");
        }
        try {
            return function.apply(key);
        } finally {
            EncryptionLoggerFactory.getInstance().rootKeyConsumed(this);
            destroy();
        }
    }

    public void destroy() {
        var key = privateKey.getAndSet(null);
        if (key != null && !key.isDestroyed()) {
            try {
                key.destroy();
            } catch (DestroyFailedException ex) {
                EncryptionLoggerFactory.getInstance().rootKeyDestroyFailed(this, ex);
            }
        }
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        throw new UnsupportedOperationException("Serialization of root keys is not allowed");
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException("Deserialization of root keys is not allowed");
    }
}
