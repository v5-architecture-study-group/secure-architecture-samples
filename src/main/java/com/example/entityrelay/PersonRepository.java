package com.example.entityrelay;

import com.example.domainprimitives.contact.EmailAddress;

import java.util.Optional;

public class PersonRepository {

    public Optional<Person> find(PersonId personId) {
        return findPersonRecord(personId).map(this::createEntityFromRecord);
    }

    private Person createEntityFromRecord(PersonRecord personRecord) {
        return switch (personRecord.maritalStatus) {
            case SINGLE -> new Person.SinglePerson(personRecord.personId, personRecord.emailAddress);
            case DATING ->
                    new Person.DatingPerson(personRecord.personId, personRecord.emailAddress, personRecord.significantOther);
            case MARRIED ->
                    new Person.MarriedPerson(personRecord.personId, personRecord.emailAddress, personRecord.significantOther);
        };
    }

    public void save(Person person) {
        switch (person) {
            case Person.SinglePerson singlePerson ->
                    savePersonRecord(new PersonRecord(singlePerson.personId(), singlePerson.emailAddress().orElse(null), MaritalStatus.SINGLE, null));
            case Person.DatingPerson datingPerson ->
                    savePersonRecord(new PersonRecord(datingPerson.personId(), datingPerson.emailAddress().orElse(null), MaritalStatus.DATING, datingPerson.date()));
            case Person.MarriedPerson marriedPerson ->
                    savePersonRecord(new PersonRecord(marriedPerson.personId(), marriedPerson.emailAddress().orElse(null), MaritalStatus.MARRIED, marriedPerson.spouse()));
        }
    }

    private Optional<PersonRecord> findPersonRecord(PersonId personId) {
        // TODO Fetch person record from database
        return Optional.empty();
    }

    private void savePersonRecord(PersonRecord personRecord) {
        // TODO Save person record to database
    }

    private enum MaritalStatus {
        SINGLE, DATING, MARRIED
    }

    private record PersonRecord(PersonId personId, EmailAddress emailAddress, MaritalStatus maritalStatus,
                                PersonId significantOther) {
    }
}
