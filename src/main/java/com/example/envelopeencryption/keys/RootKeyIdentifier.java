package com.example.envelopeencryption.keys;

import com.example.envelopeencryption.algorithm.AsymmetricAlgorithm;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record RootKeyIdentifier(UUID uuid, AsymmetricAlgorithm algorithm) {

    public RootKeyIdentifier {
        requireNonNull(uuid);
        requireNonNull(algorithm);
    }
}
