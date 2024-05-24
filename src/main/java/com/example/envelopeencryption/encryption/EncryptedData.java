package com.example.envelopeencryption.encryption;

import com.example.envelopeencryption.keys.DataKeyIdentifier;

import static java.util.Objects.requireNonNull;

public record EncryptedData(DataKeyIdentifier dataKeyIdentifier, byte[] ciphertext) {

    public EncryptedData {
        requireNonNull(dataKeyIdentifier);
        requireNonNull(ciphertext);
    }
}
