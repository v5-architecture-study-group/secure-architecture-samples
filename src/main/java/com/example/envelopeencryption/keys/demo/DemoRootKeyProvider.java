package com.example.envelopeencryption.keys.demo;

import com.example.envelopeencryption.algorithm.AsymmetricAlgorithm;
import com.example.envelopeencryption.keys.PrivateRootKey;
import com.example.envelopeencryption.keys.PublicRootKey;
import com.example.envelopeencryption.keys.RootKeyIdentifier;
import com.example.envelopeencryption.keys.RootKeyProvider;

import java.security.KeyPair;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public final class DemoRootKeyProvider implements RootKeyProvider {

    private RootKeyIdentifier identifier;
    private KeyPair keyPair;
    private PublicRootKey publicRootKey;

    public DemoRootKeyProvider(AsymmetricAlgorithm algorithm) {
        this(new RootKeyIdentifier(UUID.randomUUID(), algorithm), algorithm.generateKeyPair().orElseThrow(() -> new IllegalStateException("Failed to generate key pair")));
    }

    public DemoRootKeyProvider(RootKeyIdentifier identifier, KeyPair keyPair) {
        this.identifier = requireNonNull(identifier);
        this.keyPair = requireNonNull(keyPair);
        this.publicRootKey = new PublicRootKey(identifier, keyPair.getPublic());
    }

    public synchronized RotationResult rotate() {
        var previous = new PrivateRootKey(identifier, keyPair.getPrivate());
        identifier = new RootKeyIdentifier(UUID.randomUUID(), this.identifier.algorithm());
        keyPair = identifier.algorithm().generateKeyPair().orElseThrow(() -> new IllegalStateException("Failed to generate key pair"));
        publicRootKey = new PublicRootKey(identifier, keyPair.getPublic());
        return new RotationResult(previous, publicRootKey);
    }

    @Override
    public synchronized PublicRootKey publicRootKey() {
        return publicRootKey;
    }

    @Override
    public synchronized PrivateRootKey privateRootKey() {
        return new PrivateRootKey(identifier, keyPair.getPrivate());
    }

    public record RotationResult(PrivateRootKey previous, PublicRootKey current) {
    }
}
