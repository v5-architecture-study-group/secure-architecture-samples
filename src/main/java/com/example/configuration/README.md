# Configuration as a Domain Concern

This example defines a `DatabaseConfiguration` interface that uses domain primitives rather than strings and integers.
Any invalid configuration data will be caught as soon as the domain primitives are created and cannot be passed on to be
used for e.g. an injection attack.

Furthermore, the database password uses the read-once pattern which forces you to think about how you handle passwords
in your code. You could apply the same pattern to other secrets such as encryption and signing keys.

There is an example implementation - `PropertiesBasedDatabaseConfiguration` - that reads the configuration data from
a `Properties` object and converts it into domain primitives. You could just as well be reading from a configuration
file or from some remote configuration service or vault.

The example application does not cache anything, but creates new domain primitives everytime the methods are called.
For the password this makes sense, but for non-sensitive information you may consider caching the domain primitives
since they are immutable anyway.

There is a unit test that demonstrates how the code could be used in an application.
