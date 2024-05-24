package com.example.envelopeencryption.encryption;

import com.example.envelopeencryption.keys.DataKey;
import com.example.envelopeencryption.keys.DataKeyIdentifier;
import com.example.envelopeencryption.keys.KeyVault;

import java.nio.ByteBuffer;

import static java.util.Objects.requireNonNull;

public final class EncryptionService {

    private final KeyVault keyVault;

    public EncryptionService(KeyVault keyVault) {
        this.keyVault = requireNonNull(keyVault);
    }

    public EncryptedData encrypt(byte[] plaintext, DataKeyIdentifier dataKeyIdentifier) {
        var key = keyVault.getDataKey(dataKeyIdentifier).orElseThrow(() -> new IllegalArgumentException("Could not find key"));
        return encrypt(plaintext, key);
    }

    public EncryptedData encrypt(byte[] plaintext) {
        var key = keyVault.generateDataKey();
        return encrypt(plaintext, key);
    }

    private EncryptedData encrypt(byte[] plaintext, DataKey key) {
        var iv = key.identifier().algorithm().generateInitialVector().orElseThrow(() -> new IllegalStateException("Could not generate IV"));
        var ciphertext = key.doWithKey(secretKey -> key.identifier().algorithm().encrypt(secretKey, plaintext, iv)).orElseThrow(() -> new IllegalStateException("Could not encrypt plaintext"));
        return new EncryptedData(
                key.identifier(),
                ByteBuffer.allocate(iv.length + ciphertext.length)
                        .put(iv)
                        .put(ciphertext)
                        .array()
        );
    }

    public byte[] decrypt(EncryptedData encryptedData) {
        var key = keyVault.getDataKey(encryptedData.dataKeyIdentifier()).orElseThrow(() -> new IllegalArgumentException("Could not find key"));
        var bb = ByteBuffer.wrap(encryptedData.ciphertext());

        byte[] iv = new byte[key.identifier().algorithm().initialVectorLengthBytes()];
        bb.get(iv);

        byte[] ciphertext = new byte[bb.remaining()];
        bb.get(ciphertext);

        return key.doWithKey(secretKey -> key.identifier().algorithm().decrypt(secretKey, ciphertext, iv)).orElseThrow(() -> new IllegalStateException("Could not decrypt ciphertext"));
    }
}
