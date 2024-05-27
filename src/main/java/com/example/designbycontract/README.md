# Design by Contract

This example demonstrates how you can use Design by Contract in Java. For each operation that changes the state, you
specify the following (using e.g. Javadocs):

* Precondition: What must be true before the operation is called.
* Postcondition: What will be true after the operation has finished.
* Invariant: What must always be true.

Add checks to the code to ensure that the preconditions are met. A method should throw an exception if a precondition
is not met (like an invalid method argument). The state of the object should not change if a precondition is not met,
so this is a form of *fail fast*.

Next, implement a method for checking the invariants. This method should be called at the end of each method that
changes the state of the object and also at the end of the constructor. This method should check that all the
invariants are true and throw an exception if not. If your code is correct, this method should never throw any
exceptions - it exists only to help you find bugs in your code. If you want to, you can implement the checks as
assertions instead of throwing exceptions, and disable the assertions once you know your code is correct.

Finally, if you want to, you can add checks for the post-conditions as well, although this is less common. You could
do this using Java assertions, unit test or by checking the return value of the method in the calling code.

The example is a simple bank account class with methods for depositing and withdrawing money. Every method starts by
checking the preconditions and throws an exception if they are not met. The invariants are checked at the end of each
method and also in the constructor. They are checked using assertions. Postconditions are only checked in a unit test.