package com.example.configuration;

import com.example.domainprimitives.security.Username;
import com.example.readonce.Password;

import java.net.URI;
import java.time.Duration;
import java.util.Optional;
import java.util.Properties;

public final class PropertiesBasedDatabaseConfiguration implements DatabaseConfiguration {

    // You could also cache the domain primitives instead of reading them again. Only the password
    // might be problematic because it is a read-once value.

    private final Properties properties;

    public PropertiesBasedDatabaseConfiguration(Properties properties) {
        this.properties = properties;
    }

    @Override
    public Username username() {
        return Optional.ofNullable(properties.getProperty("jdbc.username")).map(Username::new).orElseThrow(() -> new IllegalStateException("No username found"));
    }

    @Override
    public Password password() {
        return Optional.ofNullable(properties.getProperty("jdbc.password")).map(String::toCharArray).map(Password::new).orElseThrow(() -> new IllegalStateException("No password found"));
    }

    @Override
    public URI url() {
        return Optional.ofNullable(properties.getProperty("jdbc.url")).map(URI::create).orElseThrow(() -> new IllegalStateException("No URL found"));
    }

    @Override
    public Duration loginTimeout() {
        return Duration.parse(Optional.ofNullable(properties.getProperty("jdbc.loginTimeout")).orElse("PT5S"));
    }
}
