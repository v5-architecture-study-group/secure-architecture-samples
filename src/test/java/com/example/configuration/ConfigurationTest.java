package com.example.configuration;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.format.DateTimeParseException;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ConfigurationTest {

    @Test
    void properties_are_converted_to_domain_primitives() {
        var properties = new Properties();
        properties.put("jdbc.username", "admin");
        properties.put("jdbc.password", "a_secret_password_that_is_long_enough");
        properties.put("jdbc.url", "jdbc:h2:mem:test");
        properties.put("jdbc.loginTimeout", "PT10S");

        var configuration = new PropertiesBasedDatabaseConfiguration(properties);
        assertThat(configuration.url().toString()).isEqualTo("jdbc:h2:mem:test");
        assertThat(configuration.password().value()).isEqualTo("a_secret_password_that_is_long_enough".toCharArray());
        assertThat(configuration.username().value()).isEqualTo("admin");
        assertThat(configuration.loginTimeout().getSeconds()).isEqualTo(10);
    }

    @Test
    void malformed_properties_cannot_be_converted_to_domain_primitives() {
        var properties = new Properties();
        properties.put("jdbc.username", "this is not a valid u~sername");
        properties.put("jdbc.password", "tooshort");
        properties.put("jdbc.url", "definitely not a URL");
        properties.put("jdbc.loginTimeout", "12345");

        var configuration = new PropertiesBasedDatabaseConfiguration(properties);
        assertThatThrownBy(configuration::username).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(configuration::password).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(configuration::url).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(configuration::loginTimeout).isInstanceOf(DateTimeParseException.class);
    }

    @Test
    void you_can_use_the_database_configuration_to_connect_to_a_real_database() throws SQLException {
        var properties = new Properties();
        properties.put("jdbc.username", "admin");
        properties.put("jdbc.password", "a_secret_password_that_is_long_enough");
        properties.put("jdbc.url", "jdbc:h2:mem:test");
        var configuration = new PropertiesBasedDatabaseConfiguration(properties);
        try (var connection = Database.getConnection(configuration)) {
            try (var statement = connection.createStatement()) {
                statement.execute("SELECT 1");
                try (var result = statement.getResultSet()) {
                    result.next();
                    assertThat(result.getInt(1)).isEqualTo(1);
                }
            }
        }
    }
}
