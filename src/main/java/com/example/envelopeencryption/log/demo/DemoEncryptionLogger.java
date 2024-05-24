package com.example.envelopeencryption.log.demo;

import com.example.envelopeencryption.keys.DataKey;
import com.example.envelopeencryption.keys.DataKeyIdentifier;
import com.example.envelopeencryption.keys.PrivateRootKey;
import com.example.envelopeencryption.keys.RootKeyIdentifier;
import com.example.envelopeencryption.log.EncryptionLogger;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.security.auth.DestroyFailedException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public final class DemoEncryptionLogger extends EncryptionLogger {

    // This implementation prints to the console. Don't do that in a real application.

    @Override
    public void dataKeyConsumed(DataKey dataKey) {
        System.out.println("Data key consumed: " + dataKey.identifier().uuid());
    }

    @Override
    public void dataKeyDestroyFailed(DataKey dataKey, DestroyFailedException exception) {
        System.err.println("Data key destroy failed: " + dataKey.identifier().uuid());
        exception.printStackTrace(System.err);
    }

    @Override
    public void dataKeyUpdatedBecauseOfRootKeyRotation(DataKeyIdentifier dataKeyIdentifier, RootKeyIdentifier oldRootKey, RootKeyIdentifier newRootKey) {
        System.out.println("Data key updated because of root key rotation: " + dataKeyIdentifier.uuid() + " (old root key: " + oldRootKey.uuid() + ", new root key: " + newRootKey.uuid() + ")");
    }

    @Override
    public void dataKeyCreated(DataKeyIdentifier dataKeyIdentifier, RootKeyIdentifier rootKeyIdentifier) {
        System.out.println("Data key created: " + dataKeyIdentifier.uuid() + " (using root key " + rootKeyIdentifier.uuid() + ")");
    }

    @Override
    public void missingCryptographicAlgorithm(NoSuchAlgorithmException exception) {
        System.err.println("Missing cryptographic algorithm");
        exception.printStackTrace(System.err);
    }

    @Override
    public void missingPadding(NoSuchPaddingException exception) {
        System.err.println("Missing padding");
        exception.printStackTrace(System.err);
    }

    @Override
    public void invalidAlgorithmParameter(InvalidAlgorithmParameterException exception) {
        System.err.println("Invalid algorithm parameter");
        exception.printStackTrace(System.err);
    }

    @Override
    public void invalidKey(InvalidKeyException exception) {
        System.err.println("Invalid key");
        exception.printStackTrace(System.err);
    }

    @Override
    public void illegalBlockSize(IllegalBlockSizeException exception) {
        System.err.println("Illegal block size");
        exception.printStackTrace(System.err);
    }

    @Override
    public void badPadding(BadPaddingException exception) {
        System.err.println("Bad padding");
        exception.printStackTrace(System.err);
    }

    @Override
    public void rootKeyConsumed(PrivateRootKey rootKey) {
        System.out.println("Root key consumed: " + rootKey.identifier().uuid());
    }

    @Override
    public void rootKeyDestroyFailed(PrivateRootKey rootKey, DestroyFailedException exception) {
        System.err.println("Data key destroy failed: " + rootKey.identifier().uuid());
        exception.printStackTrace(System.err);
    }

    @Override
    public void invalidKeyFormat(IllegalArgumentException exception) {
        System.out.println("Invalid key format");
        exception.printStackTrace(System.err);
    }
}
