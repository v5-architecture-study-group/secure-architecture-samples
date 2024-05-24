package com.example.envelopeencryption;

import com.example.envelopeencryption.algorithm.RSA;
import com.example.envelopeencryption.encryption.EncryptionService;
import com.example.envelopeencryption.keys.demo.DemoKeyVault;
import com.example.envelopeencryption.keys.demo.DemoRootKeyProvider;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class EncryptionTest {

    private DemoRootKeyProvider rootKeyProvider = new DemoRootKeyProvider(RSA.INSTANCE);
    private DemoKeyVault keyVault = new DemoKeyVault(rootKeyProvider);
    private EncryptionService encryptionService = new EncryptionService(keyVault);


    @Test
    void you_can_encrypt_and_decrypt_a_string() {
        var encrypted = encryptionService.encrypt("Hello, World!".getBytes(StandardCharsets.UTF_8));
        var decrypted = new String(encryptionService.decrypt(encrypted), StandardCharsets.UTF_8);
        assertThat(decrypted).isEqualTo("Hello, World!");
    }

    @Test
    void you_can_encrypt_multiple_strings_with_their_own_data_keys() {
        var encrypted1 = encryptionService.encrypt("Hello, World!".getBytes(StandardCharsets.UTF_8));
        var encrypted2 = encryptionService.encrypt("Goodbye, World!".getBytes(StandardCharsets.UTF_8));
        assertThat(encrypted1.dataKeyIdentifier().uuid()).isNotEqualTo(encrypted2.dataKeyIdentifier().uuid());
        assertThat(new String(encryptionService.decrypt(encrypted1), StandardCharsets.UTF_8)).isEqualTo("Hello, World!");
        assertThat(new String(encryptionService.decrypt(encrypted2), StandardCharsets.UTF_8)).isEqualTo("Goodbye, World!");
    }

    @Test
    void you_can_rotate_the_root_keys_without_losing_access_to_your_encrypted_data() {
        var encrypted = encryptionService.encrypt("Hello, World".getBytes(StandardCharsets.UTF_8));
        var rotationResult = rootKeyProvider.rotate();
        keyVault.rotateKeys(rotationResult.previous(), rotationResult.current());
        assertThat(new String(encryptionService.decrypt(encrypted), StandardCharsets.UTF_8)).isEqualTo("Hello, World");
    }
}
