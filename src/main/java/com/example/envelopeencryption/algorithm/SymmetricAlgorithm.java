package com.example.envelopeencryption.algorithm;

import javax.crypto.SecretKey;
import java.util.Optional;

public interface SymmetricAlgorithm {

    String name();

    Optional<SecretKey> generateKey();

    Optional<SecretKey> decodeKey(byte[] encodedKey);

    byte[] encodeKey(SecretKey key);

    int initialVectorLengthBytes();

    Optional<byte[]> generateInitialVector();

    Optional<byte[]> encrypt(SecretKey secret, byte[] plaintext, byte[] iv);

    Optional<byte[]> decrypt(SecretKey secret, byte[] ciphertext, byte[] iv);
}
