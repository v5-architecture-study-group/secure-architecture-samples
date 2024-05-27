package com.example.logging.event;

import com.example.domainprimitives.security.Username;
import com.example.logging.IpAddress;

import java.time.Instant;

public sealed interface AccountEvent {

    Instant timestamp();

    Username account();

    record AccountCreated(Instant timestamp, Username account) implements AccountEvent {
    }

    record AccountLockedBecauseOfTooManyFailedLoginAttempts(Instant timestamp,
                                                            Username account) implements AccountEvent {
    }

    record AccountLockedByAdmin(Instant timestamp, Username account, Username admin,
                                IpAddress adminHost) implements AccountEvent {
    }

    record AccountUnlockedByAdmin(Instant timestamp, Username account, Username admin,
                                  IpAddress adminHost) implements AccountEvent {
    }

    record AccountPasswordChanged(Instant timestamp, Username account, IpAddress host) implements AccountEvent {
    }

    record AccountPasswordResetByAdmin(Instant timestamp, Username account, Username admin,
                                       IpAddress adminHost) implements AccountEvent {
    }
}
