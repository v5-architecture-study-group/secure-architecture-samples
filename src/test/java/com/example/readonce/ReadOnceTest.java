package com.example.readonce;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReadOnceTest {

    @Test
    void wrapped_value_can_only_be_read_once() {
        var readOnce = new ReadOnce<>("value");
        assertThat(readOnce.value()).isEqualTo("value");
        assertThatThrownBy(readOnce::value).isInstanceOf(IllegalStateException.class);
    }
}
