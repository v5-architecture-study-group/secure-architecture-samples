package com.example.configuration;

import com.example.domainprimitives.security.Username;
import com.example.readonce.Password;

import java.net.URI;
import java.time.Duration;

public interface DatabaseConfiguration {

    Username username();

    Password password();

    URI url();

    Duration loginTimeout();
}
