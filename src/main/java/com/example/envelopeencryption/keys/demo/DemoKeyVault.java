package com.example.envelopeencryption.keys.demo;

import com.example.envelopeencryption.algorithm.AES256;
import com.example.envelopeencryption.keys.*;
import com.example.envelopeencryption.log.EncryptionLogger;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.util.Objects.requireNonNull;

public final class DemoKeyVault implements KeyVault {

    private final ConcurrentMap<DataKeyIdentifier, byte[]> dataKeys = new ConcurrentHashMap<>();
    private final RootKeyProvider rootKeyProvider;

    public DemoKeyVault(RootKeyProvider rootKeyProvider) {
        this.rootKeyProvider = requireNonNull(rootKeyProvider);
    }

    @Override
    public Optional<DataKey> getDataKey(DataKeyIdentifier dataKeyIdentifier) {
        var ciphertextDataKey = dataKeys.get(dataKeyIdentifier);
        if (ciphertextDataKey == null) {
            return Optional.empty();
        }
        var rootKey = rootKeyProvider.privateRootKey();
        var plaintextDataKey = rootKey.doWithPrivateKey(privateKey -> rootKey.identifier().algorithm().decrypt(privateKey, ciphertextDataKey)).orElseThrow(() -> new IllegalStateException("Failed to decrypt key"));
        try {
            var dataKey = dataKeyIdentifier.algorithm().decodeKey(plaintextDataKey).orElseThrow(() -> new IllegalStateException("Failed to decode key"));
            return Optional.of(new DataKey(dataKeyIdentifier, dataKey));
        } finally {
            Arrays.fill(plaintextDataKey, (byte) 0);
        }
    }

    @Override
    public DataKey generateDataKey() {
        var dataKey = AES256.INSTANCE.generateKey().orElseThrow(() -> new IllegalStateException("Failed to generate key"));
        var identifier = new DataKeyIdentifier(UUID.randomUUID(), AES256.INSTANCE);
        var rootKey = rootKeyProvider.publicRootKey();
        dataKeys.put(identifier, encryptDataKey(rootKey, identifier, dataKey));
        EncryptionLogger.getInstance().dataKeyCreated(identifier, rootKey.identifier());
        return new DataKey(identifier, dataKey);
    }

    private byte[] encryptDataKey(PublicRootKey rootKey, DataKeyIdentifier identifier, SecretKey dataKey) {
        var plaintextDataKey = identifier.algorithm().encodeKey(dataKey);
        try {
            return rootKey.identifier().algorithm().encrypt(rootKey.publicKey(), plaintextDataKey).orElseThrow(() -> new IllegalStateException("Failed to encrypt key"));
        } finally {
            Arrays.fill(plaintextDataKey, (byte) 0);
        }
    }

    public void rotateKeys(PrivateRootKey oldRootKey, PublicRootKey newRootKey) {
        oldRootKey.doWithPrivateKey(oldPrivateKey -> {
            dataKeys.forEach((identifier, encryptedDataKey) -> {
                var plaintextDataKey = oldRootKey.identifier().algorithm().decrypt(oldPrivateKey, encryptedDataKey).orElseThrow(() -> new IllegalStateException("Failed to decrypt key"));
                try {
                    var ciphertextDataKey = newRootKey.identifier().algorithm().encrypt(newRootKey.publicKey(), plaintextDataKey).orElseThrow(() -> new IllegalStateException("Failed to encrypt key"));
                    dataKeys.put(identifier, ciphertextDataKey);
                    EncryptionLogger.getInstance().dataKeyUpdatedBecauseOfRootKeyRotation(identifier, oldRootKey.identifier(), newRootKey.identifier());
                } finally {
                    Arrays.fill(plaintextDataKey, (byte) 0);
                }
            });
            return null;
        });
    }
}
