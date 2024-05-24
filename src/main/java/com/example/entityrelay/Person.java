package com.example.entityrelay;

import com.example.domainprimitives.contact.EmailAddress;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

public sealed abstract class Person {

    private final PersonId personId;
    private EmailAddress emailAddress;

    public Person(PersonId personId, EmailAddress emailAddress) {
        this.personId = requireNonNull(personId);
        this.emailAddress = emailAddress;
    }

    public Person(Person original) {
        this.personId = original.personId;
        this.emailAddress = original.emailAddress;
    }

    public PersonId personId() {
        return personId;
    }

    public Optional<EmailAddress> emailAddress() {
        return Optional.ofNullable(emailAddress);
    }

    // You can have mutable state if you want to

    public void setEmailAddress(EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
    }

    public static final class SinglePerson extends Person {

        public SinglePerson(PersonId personId, EmailAddress emailAddress) {
            super(personId, emailAddress);
        }

        private SinglePerson(Person original) {
            super(original);
        }

        public DatingPerson date(SinglePerson person) {
            return new DatingPerson(this, person.personId());
        }

        public MarriedPerson marry(SinglePerson person) {
            return new MarriedPerson(this, person.personId());
        }
    }

    public static final class DatingPerson extends Person {

        private final PersonId date;

        public DatingPerson(PersonId personId, EmailAddress emailAddress, PersonId date) {
            super(personId, emailAddress);
            this.date = date;
        }

        private DatingPerson(Person original, PersonId date) {
            super(original);
            this.date = requireNonNull(date);
        }

        public MarriedPerson marry() {
            return new MarriedPerson(this, date);
        }

        public SinglePerson breakup() {
            return new SinglePerson(this);
        }

        public PersonId date() {
            return date;
        }
    }

    public static final class MarriedPerson extends Person {

        private final PersonId spouse;

        public MarriedPerson(PersonId personId, EmailAddress emailAddress, PersonId spouse) {
            super(personId, emailAddress);
            this.spouse = spouse;
        }

        private MarriedPerson(Person original, PersonId spouse) {
            super(original);
            this.spouse = requireNonNull(spouse);
        }

        public SinglePerson divorce() {
            return new SinglePerson(this);
        }

        public PersonId spouse() {
            return spouse;
        }
    }

    public static SinglePerson create(PersonId personId, EmailAddress emailAddress) {
        return new SinglePerson(personId, emailAddress);
    }
}
