package com.example.envelopeencryption.keys;

import java.security.PublicKey;

import static java.util.Objects.requireNonNull;

public record PublicRootKey(RootKeyIdentifier identifier, PublicKey publicKey) implements RootKey {

    public PublicRootKey {
        requireNonNull(identifier);
        requireNonNull(publicKey);
    }
}
