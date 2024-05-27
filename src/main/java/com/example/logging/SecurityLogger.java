package com.example.logging;

import com.example.logging.event.AccountEvent;
import com.example.logging.event.AuthenticationEvent;

public interface SecurityLogger {

    void logAuthenticationEvent(AuthenticationEvent authenticationEvent);

    void logAccountEvent(AccountEvent accountEvent);
}
