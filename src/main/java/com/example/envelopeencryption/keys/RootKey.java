package com.example.envelopeencryption.keys;

public sealed interface RootKey permits PrivateRootKey, PublicRootKey {

    RootKeyIdentifier identifier();

}
