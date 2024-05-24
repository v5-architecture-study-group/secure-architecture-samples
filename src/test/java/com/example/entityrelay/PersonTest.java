package com.example.entityrelay;

import com.example.domainprimitives.contact.EmailAddress;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonTest {

    private static final EmailAddress ALICE = EmailAddress.fromString("alice@acme.com");
    private static final EmailAddress BOB = EmailAddress.fromString("bob@example.com");
    private static final EmailAddress EVE = EmailAddress.fromString("eve@haxxer.pro");

    private final Person.SinglePerson alice = Person.create(PersonId.randomId(), ALICE);
    private final Person.SinglePerson bob = Person.create(PersonId.randomId(), BOB);
    private final Person.SinglePerson eve = Person.create(PersonId.randomId(), EVE);

    @Test
    void you_can_date_if_you_are_single() {
        Person.DatingPerson datingBob = bob.date(alice);
        assertThat(datingBob.personId()).isEqualTo(bob.personId());
        assertThat(datingBob.date()).isEqualTo(alice.personId());
    }

    @Test
    void you_can_breakup_if_you_are_dating() {
        Person.DatingPerson datingBob = bob.date(alice);
        Person.SinglePerson singleBob = datingBob.breakup();
        assertThat(singleBob.personId()).isEqualTo(datingBob.personId());
    }

    @Test
    void you_can_marry_if_you_are_dating() {
        Person.DatingPerson datingBob = bob.date(alice);
        Person.MarriedPerson marriedBob = datingBob.marry();
        assertThat(marriedBob.personId()).isEqualTo(datingBob.personId());
        assertThat(marriedBob.spouse()).isEqualTo(alice.personId());
    }

    @Test
    void you_can_marry_if_you_are_single() {
        Person.MarriedPerson marriedBob = bob.marry(alice);
        assertThat(marriedBob.personId()).isEqualTo(bob.personId());
        assertThat(marriedBob.spouse()).isEqualTo(alice.personId());
    }

    @Test
    void you_can_divorce_if_you_are_married() {
        Person.MarriedPerson marriedBob = bob.marry(alice);
        Person.SinglePerson divorcedBob = marriedBob.divorce();
        assertThat(divorcedBob.personId()).isEqualTo(bob.personId());
    }

    @Test
    void you_can_date_after_divorcing() {
        Person.MarriedPerson marriedBob = bob.marry(alice);
        Person.SinglePerson divorcedBob = marriedBob.divorce();
        Person.DatingPerson datingBob = divorcedBob.date(eve);
        assertThat(datingBob.personId()).isEqualTo(bob.personId());
        assertThat(datingBob.date()).isEqualTo(eve.personId());
    }

    // The compiler will not allow you to make illegal transitions!
}
