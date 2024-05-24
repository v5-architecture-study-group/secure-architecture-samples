# The Read-Once Pattern

The read-once pattern is used for sensitive values that can only be consumed once in the code. This is useful to
detect and prevent accidental misuse, such as logging the value.

This example contains two ways of implementing the read-once pattern: using a character array and using an
`AtomicReference`.

The `Password` class uses a character array to store the password. The password is cleared from memory as soon as
it has been accessed. This is done by overwriting the array with zeros. This reduces the risk of leaving sensitive
information in memory to be read by an attacker. Of course, it does not remove the risk completely in case the code
that accesses the password forgets to clear its copy.

The `ReadOnce` class is a generic wrapper class that accepts any object and stores it in an `AtomicReference`. The
reference is set to `null` after the object has been accessed for the first time. The object may remain in the memory,
but it cannot be accessed again without throwing an exception.

