# Domain Primitives

Domain primitives are immutable value objects that give business meaning to primitive types, like integer,
string, etc. They are used to represent domain concepts and enforce business rules. Domain primitives are one of the
most powerful tools in making the code more robust and more secure. They can be used regardless of whether you are using
domain-driven design (DDD) or not.

A domain primitive always validates its input in the constructor. Thus, if a domain primitive exists, it is always
at least syntactically valid (additional semantic validation may still be required, for example to check whether a
bank account actually exists even though it is in a valid format).

Recall that input validation should consist of the following step:

- Origin - where does the data come from?
- Size - is the data within reasonable limits (not too small, not too big)?
- Lexical content - does the data contain only allowed characters?
- Syntax - does the data conform to the expected format?
- Semantics - does the data make sense?

Domain primitives typically validate the size, lexical content and syntax inside their constructors. The origin should
be validated before the raw data even reaches the domain primitive's constructor. The semantics can be validated by
the constructor if it has enough information to do so. Otherwise, the object creating the domain primitive should do
the semantic validation.

Domain primitives can also be composed of other domain primitives. For example, an amount of money typically has a
magnitude and a currency. The magnitude may be a `BigDecimal` whereas the currency may be a domain primitive of its own
(different currencies have different numbers of decimals).

Domain primitives are not only wrappers of data. They can also contain business logic and enforce business rules.
For example, a `Money` domain primitive may have methods to add and subtract money, and enforce that the currency of
both operands is the same. Multiplication and division by a scalar may also be allowed and in this case, the domain
primitive would make sure that the result is rounded correctly. If you were to use a `BigDecimal` directly, you would
have to remember to take care of all this every time you perform an operation. The compiler would also not prevent
you from e.g. adding two sums of money with different currencies, or adding a sum of money and a quantity of apples
together.
