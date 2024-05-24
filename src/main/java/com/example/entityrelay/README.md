# Entity Relay

Many bugs and security vulnerabilities are caused by the software entering an unexpected state. This can happen
when the software is not properly designed to handle all possible states and transitions between them. One way to
address this problem is to use entity relay, which is demonstrated by this example.

When using entity relay, you don't have a single entity class that changes its state. Instead, you have different
entity classes for different states - or groups of states. As an entity changes its state, it is replaced by a new
entity of the appropriate class, handing over its data to the new entity (hence the name "relay"). The entity ID is
also handed over, which makes it possible to identify the entity across state changes.

The benefit of this approach is that the compiler prevents you from doing illegal state transitions. Since each state
has its own type, you can declare only the methods that are allowed in that state. By using these types in method
parameters, you can also control the state of entities that are passed to methods.

This approach works for entities that transition from one state to another in a linear fashion. If an entity needs to
handle multiple different states at the same time, and these states are not related, you should use something else,
like [entity state objects](../entitystateobject).

The example in this case deals with marital status. A person can be either single, dating or married. The allowed
transitions are the following:

- a single person can start dating another single person
- a single person can marry another single person without dating (arranged marriage)
- a person who is dating can marry the person they are dating
- a person who is dating can break up from the person they are dating
- a married person can divorce their spouse

All other transitions are not allowed and prevented by the compiler. To keep things simple, this example does not
change the state of the other person. To do that, you would probably model the relationship as a separate entity and
deduce the marital status based on that.

There is a unit test that covers all possible combinations of states and transitions.

Persisting entities that use entity relay is a bit more complicated than persisting entities that are represented by
a single concrete class at all times. A stub of a repository is provided in this example to give you some hints on how
to do this.
