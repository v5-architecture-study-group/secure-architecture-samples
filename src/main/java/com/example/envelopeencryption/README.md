# Envelope Encryption

First a word of caution: Don't write your own encryption code if there is a battle tested encryption library available.
This example _tries_ to do it properly (mostly), but its intention is to demonstrate the principles of envelope
encryption, not to provide a production ready encryption library. It also uses other patterns such as:

- Domain primitives
- Read-once
- Logging as a domain concern

The idea of envelope encryption is to encrypt the data with a data key, and then encrypt the data with a root key.
The root key can then be rotated without having to re-encrypt the data - only the data keys need to be re-encrypted.
You can have as many data keys as you need, which has the added benefit of reducing the amount of data encrypted
by a single key. Thus, if the dataset is compromised, you need multiple keys to decrypt all of it. Likewise, if a key
is compromised, only a part of the dataset can be decrypted.

## Algorithms

The data is encrypted with symmetric encryption, which is fast. In this example, AES with 256-bit keys is used.
The data keys themselves are encrypted with asymmetric encryption, which is slower, but has the benefit of not needing
access to a private key to encrypt data. In this example, RSA with 2048-bit keys is used. The Java Cryptography API is
not really developer friendly, so custom wrappers are used to make it easier to use. The classes related to algorithms
can be found in the `algorithm` subpackage.

## Encryption and Decryption

The code that handles encryption and decryption can be bound in the `encryption` subpackage. A developer would get an
instance of `EncryptionService` (intended to be used as a singleton) and ask it to encrypt and decrypt data. Data can be
encrypted with an existing data key, or a new one can be generated. The developer has to keep track of which key
has been used to encrypt which data. UUIDs are used to identify different data keys.

## Key Management

The encryption service retrieves its data keys from a key vault. In a real-world application, this would likely be a
heavily secured remote service. In this example, the key vault is a simple in-memory store. It provides the following
features:

- Generate new data keys and encrypt them with the public root key
- Fetch existing data keys and decrypt them with the private root key
- Re-encrypt data keys with a new root key in response to root key rotation

The root key itself is provided by a root key provider, which also is a simple in-memory store. All the code related to
key management can be found in the `keys` subpackage.

## Logging

A separate domain-aware logger service is used to log events. Only specific events can be logged and domain primitives
are used to pass the information that needs to be logged. This forces you to think about what you log and why you log
it. The implementation is very much not production ready as it only writes to STDOUT and STDERR. The code related to
logging can be found in the `log` subpackage.