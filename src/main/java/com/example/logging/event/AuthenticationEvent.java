package com.example.logging.event;

import com.example.domainprimitives.security.Username;
import com.example.logging.AuthenticationMethod;
import com.example.logging.IpAddress;

import java.time.Instant;

public sealed interface AuthenticationEvent {

    Instant timestamp();

    Username account();

    AuthenticationMethod authenticationMethod();

    IpAddress host();

    record SuccessfulLogin(Instant timestamp, Username account, AuthenticationMethod authenticationMethod,
                           IpAddress host) implements AuthenticationEvent {
    }

    record FailedLoginAttempt(Instant timestamp, Username account, AuthenticationMethod authenticationMethod,
                              IpAddress host) implements AuthenticationEvent {

    }
}
