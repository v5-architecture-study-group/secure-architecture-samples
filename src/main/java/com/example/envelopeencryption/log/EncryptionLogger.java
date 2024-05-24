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

public interface EncryptionLogger {

    void dataKeyConsumed(DataKey dataKey);

    void dataKeyDestroyFailed(DataKey dataKey, DestroyFailedException exception);

    void dataKeyUpdatedBecauseOfRootKeyRotation(DataKeyIdentifier dataKeyIdentifier, RootKeyIdentifier oldRootKey, RootKeyIdentifier newRootKey);

    void dataKeyCreated(DataKeyIdentifier dataKeyIdentifier, RootKeyIdentifier rootKeyIdentifier);

    void missingCryptographicAlgorithm(NoSuchAlgorithmException exception);

    void missingPadding(NoSuchPaddingException exception);

    void invalidAlgorithmParameter(InvalidAlgorithmParameterException exception);

    void invalidKey(InvalidKeyException exception);

    void illegalBlockSize(IllegalBlockSizeException exception);

    void badPadding(BadPaddingException exception);

    void rootKeyConsumed(PrivateRootKey rootKey);

    void rootKeyDestroyFailed(PrivateRootKey rootKey, DestroyFailedException exception);

    void invalidKeyFormat(IllegalArgumentException exception);
}
