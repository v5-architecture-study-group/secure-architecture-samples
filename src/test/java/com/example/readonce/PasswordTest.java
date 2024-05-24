package com.example.readonce;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PasswordTest {

    @Test
    void password_can_only_be_read_once() {
        var password = new Password("this is a secret".toCharArray());
        assertThat(password.value()).isEqualTo("this is a secret".toCharArray());
        assertThatThrownBy(password::value).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void changing_the_input_array_does_not_affect_the_stored_array() {
        final var input = "this is a secret".toCharArray();
        var password = new Password(input);
        input[0] = 'T';
        assertThat(password.value()).isEqualTo("this is a secret".toCharArray());
        assertThat(input).isEqualTo("This is a secret".toCharArray());
    }

    @Test
    void passwords_cannot_be_serialized() throws IOException {
        try (var output = new ObjectOutputStream(new ByteArrayOutputStream())) {
            var password = new Password("this is a secret".toCharArray());
            assertThatThrownBy(() -> output.writeObject(password)).isInstanceOf(UnsupportedOperationException.class);
        }
    }
}
