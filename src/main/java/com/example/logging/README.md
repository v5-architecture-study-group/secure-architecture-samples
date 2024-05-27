# Logging as a Domain Concern

This example explains how you use domain services for logging. Instead of using a general purpose logging
framework (such as Slf4j) directly, you define an API for every logging event you are interested in. This
example uses sealed interfaces and records for the events, but you could also define the events as methods
in the service (the [envelope encryption](../envelopeencryption) example does this). This has the following benefits:

- You actually have to think about what to log and why
- You can use domain primitives, which reduces (but does not eliminate) the risk of log injection attacks
- You can easily change the logging implementation, e.g. to log to a different system or to log to a different level
- You can easily test the logging, because you can mock the logging service

This example has no implementation, as the focus is on the API.
