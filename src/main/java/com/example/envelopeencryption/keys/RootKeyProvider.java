package com.example.envelopeencryption.keys;

public interface RootKeyProvider {

    PublicRootKey publicRootKey();

    PrivateRootKey privateRootKey();
}
