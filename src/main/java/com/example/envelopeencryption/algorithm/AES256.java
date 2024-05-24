package com.example.envelopeencryption.algorithm;

import com.example.envelopeencryption.log.EncryptionLoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;

public final class AES256 implements SymmetricAlgorithm {

    public static final SymmetricAlgorithm INSTANCE = new AES256();
    private static final String CIPHER_TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BITS = 128;
    private static final int KEY_SIZE_BITS = 256;
    private static final int IV_LENGTH_BYTES = 12;

    private AES256() {
    }

    @Override
    public String name() {
        return "AES-256";
    }

    @Override
    public Optional<SecretKey> generateKey() {
        try {
            var keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(KEY_SIZE_BITS, SecureRandom.getInstanceStrong());
            return Optional.of(keyGen.generateKey());
        } catch (NoSuchAlgorithmException ex) {
            EncryptionLoggerFactory.getInstance().missingCryptographicAlgorithm(ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<SecretKey> decodeKey(byte[] encodedKey) {
        try {
            return Optional.of(new SecretKeySpec(encodedKey, "AES"));
        } catch (IllegalArgumentException ex) {
            EncryptionLoggerFactory.getInstance().invalidKeyFormat(ex);
        }
        return Optional.empty();
    }

    @Override
    public byte[] encodeKey(SecretKey key) {
        return key.getEncoded();
    }

    @Override
    public Optional<byte[]> generateInitialVector() {
        var iv = new byte[IV_LENGTH_BYTES];
        try {
            SecureRandom.getInstanceStrong().nextBytes(iv);
            return Optional.of(iv);
        } catch (NoSuchAlgorithmException ex) {
            EncryptionLoggerFactory.getInstance().missingCryptographicAlgorithm(ex);
        }
        return Optional.empty();
    }

    @Override
    public int initialVectorLengthBytes() {
        return IV_LENGTH_BYTES;
    }

    @Override
    public Optional<byte[]> encrypt(SecretKey secret, byte[] plaintext, byte[] iv) {
        try {
            var cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secret, new GCMParameterSpec(TAG_LENGTH_BITS, iv));
            return Optional.of(cipher.doFinal(plaintext));
        } catch (NoSuchAlgorithmException ex) {
            EncryptionLoggerFactory.getInstance().missingCryptographicAlgorithm(ex);
        } catch (NoSuchPaddingException ex) {
            EncryptionLoggerFactory.getInstance().missingPadding(ex);
        } catch (InvalidAlgorithmParameterException ex) {
            EncryptionLoggerFactory.getInstance().invalidAlgorithmParameter(ex);
        } catch (InvalidKeyException ex) {
            EncryptionLoggerFactory.getInstance().invalidKey(ex);
        } catch (IllegalBlockSizeException ex) {
            EncryptionLoggerFactory.getInstance().illegalBlockSize(ex);
        } catch (BadPaddingException ex) {
            EncryptionLoggerFactory.getInstance().badPadding(ex);
        }
        return Optional.empty();
    }

    @Override
    public Optional<byte[]> decrypt(SecretKey secret, byte[] ciphertext, byte[] iv) {
        try {
            var cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secret, new GCMParameterSpec(TAG_LENGTH_BITS, iv));
            return Optional.of(cipher.doFinal(ciphertext));
        } catch (NoSuchAlgorithmException ex) {
            EncryptionLoggerFactory.getInstance().missingCryptographicAlgorithm(ex);
        } catch (NoSuchPaddingException ex) {
            EncryptionLoggerFactory.getInstance().missingPadding(ex);
        } catch (InvalidAlgorithmParameterException ex) {
            EncryptionLoggerFactory.getInstance().invalidAlgorithmParameter(ex);
        } catch (InvalidKeyException ex) {
            EncryptionLoggerFactory.getInstance().invalidKey(ex);
        } catch (IllegalBlockSizeException ex) {
            EncryptionLoggerFactory.getInstance().illegalBlockSize(ex);
        } catch (BadPaddingException ex) {
            EncryptionLoggerFactory.getInstance().badPadding(ex);
        }
        return Optional.empty();
    }
}
