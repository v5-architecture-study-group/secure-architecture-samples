package com.example.envelopeencryption.algorithm;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Optional;

public interface AsymmetricAlgorithm {

    String name();

    Optional<KeyPair> generateKeyPair();

    Optional<byte[]> encrypt(PublicKey publicKey, byte[] plaintext);

    Optional<byte[]> decrypt(PrivateKey privateKey, byte[] ciphertext);
}
