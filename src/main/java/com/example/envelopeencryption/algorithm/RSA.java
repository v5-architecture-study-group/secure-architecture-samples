package com.example.envelopeencryption.algorithm;

import com.example.envelopeencryption.log.EncryptionLogger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.util.Optional;

public final class RSA implements AsymmetricAlgorithm {

    public static final AsymmetricAlgorithm INSTANCE = new RSA();
    private static final String CIPHER_TRANSFORMATION = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";

    private RSA() {
    }

    @Override
    public String name() {
        return "RSA";
    }

    @Override
    public Optional<KeyPair> generateKeyPair() {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(2048);
            return Optional.ofNullable(keyPairGen.generateKeyPair());
        } catch (NoSuchAlgorithmException ex) {
            EncryptionLogger.getInstance().missingCryptographicAlgorithm(ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<byte[]> encrypt(PublicKey publicKey, byte[] plaintext) {
        try {
            var cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Optional.of(cipher.doFinal(plaintext));
        } catch (NoSuchAlgorithmException ex) {
            EncryptionLogger.getInstance().missingCryptographicAlgorithm(ex);
        } catch (NoSuchPaddingException ex) {
            EncryptionLogger.getInstance().missingPadding(ex);
        } catch (InvalidKeyException ex) {
            EncryptionLogger.getInstance().invalidKey(ex);
        } catch (IllegalBlockSizeException ex) {
            EncryptionLogger.getInstance().illegalBlockSize(ex);
        } catch (BadPaddingException ex) {
            EncryptionLogger.getInstance().badPadding(ex);
        }
        return Optional.empty();
    }

    @Override
    public Optional<byte[]> decrypt(PrivateKey privateKey, byte[] ciphertext) {
        try {
            var cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return Optional.of(cipher.doFinal(ciphertext));
        } catch (NoSuchAlgorithmException ex) {
            EncryptionLogger.getInstance().missingCryptographicAlgorithm(ex);
        } catch (NoSuchPaddingException ex) {
            EncryptionLogger.getInstance().missingPadding(ex);
        } catch (InvalidKeyException ex) {
            EncryptionLogger.getInstance().invalidKey(ex);
        } catch (IllegalBlockSizeException ex) {
            EncryptionLogger.getInstance().illegalBlockSize(ex);
        } catch (BadPaddingException ex) {
            EncryptionLogger.getInstance().badPadding(ex);
        }
        return Optional.empty();
    }
}
