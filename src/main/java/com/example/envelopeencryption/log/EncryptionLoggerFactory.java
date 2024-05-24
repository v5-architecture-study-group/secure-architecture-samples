package com.example.envelopeencryption.log;

import com.example.envelopeencryption.log.demo.DemoEncryptionLogger;

public final class EncryptionLoggerFactory {

    private static final EncryptionLogger INSTANCE = new DemoEncryptionLogger();

    private EncryptionLoggerFactory() {
    }

    public static EncryptionLogger getInstance() {
        return INSTANCE;
    }
}
