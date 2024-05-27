package com.example.logging;

import com.example.logging.event.AccountEvent;
import com.example.logging.event.AuthenticationEvent;

public class SomeSecurityLoggerImplementation implements SecurityLogger {

    // This is just a stub.

    @Override
    public void logAuthenticationEvent(AuthenticationEvent authenticationEvent) {
        switch (authenticationEvent) {
            case AuthenticationEvent.FailedLoginAttempt failedLoginAttempt -> {
            }
            case AuthenticationEvent.SuccessfulLogin successfulLogin -> {
            }
        }
    }

    @Override
    public void logAccountEvent(AccountEvent accountEvent) {
        switch (accountEvent) {
            case AccountEvent.AccountCreated accountCreated -> {
            }
            case AccountEvent.AccountLockedBecauseOfTooManyFailedLoginAttempts accountLockedBecauseOfTooManyFailedLoginAttempts -> {
            }
            case AccountEvent.AccountLockedByAdmin accountLockedByAdmin -> {
            }
            case AccountEvent.AccountUnlockedByAdmin accountUnlockedByAdmin -> {
            }
            case AccountEvent.AccountPasswordChanged accountPasswordChanged -> {
            }
            case AccountEvent.AccountPasswordResetByAdmin accountPasswordResetByAdmin -> {
            }
        }
    }
}
