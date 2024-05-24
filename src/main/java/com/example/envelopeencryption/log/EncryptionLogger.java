package com.example.envelopeencryption.log;


import com.example.envelopeencryption.keys.DataKey;
import com.example.envelopeencryption.keys.DataKeyIdentifier;
import com.example.envelopeencryption.keys.PrivateRootKey;
import com.example.envelopeencryption.keys.RootKeyIdentifier;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.security.auth.DestroyFailedException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ServiceLoader;

public abstract class EncryptionLogger {

    public abstract void dataKeyConsumed(DataKey dataKey);

    public abstract void dataKeyDestroyFailed(DataKey dataKey, DestroyFailedException exception);

    public abstract void dataKeyUpdatedBecauseOfRootKeyRotation(DataKeyIdentifier dataKeyIdentifier, RootKeyIdentifier oldRootKey, RootKeyIdentifier newRootKey);

    public abstract void dataKeyCreated(DataKeyIdentifier dataKeyIdentifier, RootKeyIdentifier rootKeyIdentifier);

    public abstract void missingCryptographicAlgorithm(NoSuchAlgorithmException exception);

    public abstract void missingPadding(NoSuchPaddingException exception);

    public abstract void invalidAlgorithmParameter(InvalidAlgorithmParameterException exception);

    public abstract void invalidKey(InvalidKeyException exception);

    public abstract void illegalBlockSize(IllegalBlockSizeException exception);

    public abstract void badPadding(BadPaddingException exception);

    public abstract void rootKeyConsumed(PrivateRootKey rootKey);

    public abstract void rootKeyDestroyFailed(PrivateRootKey rootKey, DestroyFailedException exception);

    public abstract void invalidKeyFormat(IllegalArgumentException exception);

    public static EncryptionLogger getInstance() {
        return ServiceLoader.load(EncryptionLogger.class).findFirst().orElseThrow(() -> new IllegalStateException("No EncryptionLogger available"));
    }
}
