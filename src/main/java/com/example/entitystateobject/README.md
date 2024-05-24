# Entity State Object

Many bugs and security vulnerabilities are caused by the software entering an unexpected state. This can happen
when the software is not properly designed to handle all possible states and transitions between them. One way to
address this problem is to use entity state objects, which is demonstrated by this example.

In an entity state object, a particular state and all of its transitions are moved to a class of its own.
The entity then delegates to the state object to check whether a transition is allowed before it does anything.
The benefit of this is that the rules for state transitions are encapsulated in a single place. This makes the code
easier to test and reduces the risk of mistakes, especially if the entity has many states and transitions.

The example in this case deals with marital status. A person can be either single, dating or married. The allowed
transitions are the following:

- a single person can start dating
- a single person can marry without dating (arranged marriage)
- a person who is dating can marry
- a person who is dating can break up
- a married person can divorce

All other transitions are not allowed and will result in an `IllegalStateException`. More advanced checks, like
making sure that you can't marry someone who is already married, or that if you are dating, you are in fact marrying
the person you are currently dating, are not implemented in this example.

There is a unit test that covers all possible combinations of states and transitions.
