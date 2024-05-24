package com.example.envelopeencryption.keys;


import java.util.Optional;

public interface KeyVault {

    Optional<DataKey> getDataKey(DataKeyIdentifier dataKeyIdentifier);

    DataKey generateDataKey();
}
