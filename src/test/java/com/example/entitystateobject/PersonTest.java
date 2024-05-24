package com.example.entitystateobject;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PersonTest {

    @Test
    void you_start_out_as_single() {
        var person = new Person();
        assertThat(person.maritalStatus().state()).isEqualTo(MaritalStatus.State.SINGLE);
        assertThat(person.significantOther()).isEmpty();
    }

    @Test
    void you_can_date_if_you_are_single() {
        var p1 = new Person();
        var p2 = new Person();
        p1.date(p2);
        assertThat(p1.maritalStatus().state()).isEqualTo(MaritalStatus.State.DATING);
        assertThat(p1.significantOther()).contains(p2);
    }

    @Test
    void you_can_breakup_if_you_are_dating() {
        var p1 = new Person();
        var p2 = new Person();
        p1.date(p2);
        p1.breakup();
        assertThat(p1.maritalStatus().state()).isEqualTo(MaritalStatus.State.SINGLE);
        assertThat(p1.significantOther()).isEmpty();
    }

    @Test
    void you_can_marry_if_you_are_dating() {
        var p1 = new Person();
        var p2 = new Person();
        p1.date(p1);
        p1.marry(p2);
        assertThat(p1.maritalStatus().state()).isEqualTo(MaritalStatus.State.MARRIED);
        assertThat(p1.significantOther()).contains(p2);
    }

    @Test
    void you_can_marry_if_you_are_single() {
        var p1 = new Person();
        var p2 = new Person();
        p1.marry(p2);
        assertThat(p1.maritalStatus().state()).isEqualTo(MaritalStatus.State.MARRIED);
        assertThat(p1.significantOther()).contains(p2);
    }

    @Test
    void you_can_divorce_if_you_are_married() {
        var p1 = new Person();
        var p2 = new Person();
        p1.marry(p2);
        p1.divorce();
        assertThat(p1.maritalStatus().state()).isEqualTo(MaritalStatus.State.SINGLE);
        assertThat(p1.significantOther()).isEmpty();
    }

    @Test
    void you_can_date_after_divorcing() {
        var p1 = new Person();
        var p2 = new Person();
        var p3 = new Person();
        p1.marry(p2);
        p1.divorce();
        p1.date(p3);
        assertThat(p1.maritalStatus().state()).isEqualTo(MaritalStatus.State.DATING);
        assertThat(p1.significantOther()).contains(p3);
    }

    @Test
    void you_cannot_divorce_if_you_are_single() {
        var person = new Person();
        assertThatThrownBy(person::divorce).isInstanceOf(IllegalStateException.class);
        assertThat(person.maritalStatus().state()).isEqualTo(MaritalStatus.State.SINGLE);
        assertThat(person.significantOther()).isEmpty();
    }

    @Test
    void you_cannot_breakup_if_you_are_single() {
        var person = new Person();
        assertThatThrownBy(person::breakup).isInstanceOf(IllegalStateException.class);
        assertThat(person.maritalStatus().state()).isEqualTo(MaritalStatus.State.SINGLE);
        assertThat(person.significantOther()).isEmpty();
    }

    @Test
    void you_cannot_breakup_if_you_are_married() {
        var p1 = new Person();
        var p2 = new Person();
        p1.marry(p2);
        assertThatThrownBy(p1::breakup).isInstanceOf(IllegalStateException.class);
        assertThat(p1.maritalStatus().state()).isEqualTo(MaritalStatus.State.MARRIED);
        assertThat(p1.significantOther()).contains(p2);
    }

    @Test
    void you_cannot_date_if_you_are_married() {
        var p1 = new Person();
        var p2 = new Person();
        var p3 = new Person();
        p1.marry(p2);
        assertThatThrownBy(() -> p1.date(p3)).isInstanceOf(IllegalStateException.class);
        assertThat(p1.maritalStatus().state()).isEqualTo(MaritalStatus.State.MARRIED);
        assertThat(p1.significantOther()).contains(p2);
    }
}
