package com.example.entitystateobject;

import java.util.Optional;

public class Person {

    private final MaritalStatus maritalStatus = new MaritalStatus();
    private Person significantOther;

    // You could have a lot of other attributes here, maybe other states, etc. In that case, it makes sense
    // to move the state management code into its own class.

    public void marry(Person spouse) {
        maritalStatus.marry();
        this.significantOther = spouse;
    }

    public void date(Person date) {
        maritalStatus.date();
        significantOther = date;
    }

    public void breakup() {
        maritalStatus.breakup();
        significantOther = null;
    }

    public void divorce() {
        maritalStatus.divorce();
        significantOther = null;
    }

    public MaritalStatus maritalStatus() {
        return maritalStatus;
    }

    public Optional<Person> significantOther() {
        return Optional.ofNullable(significantOther);
    }
}
