package com.example.envelopeencryption.keys;

import com.example.envelopeencryption.algorithm.SymmetricAlgorithm;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record DataKeyIdentifier(UUID uuid, SymmetricAlgorithm algorithm) {

    public DataKeyIdentifier {
        requireNonNull(uuid);
        requireNonNull(algorithm);
    }
}
