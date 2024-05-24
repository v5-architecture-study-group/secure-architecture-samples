package com.example.entitystateobject;

public class MaritalStatus {

    private State state = State.SINGLE;

    void date() {
        checkState(state == State.SINGLE, "Not appropriate to date if you are not single");
        state = State.DATING;
    }

    void marry() {
        checkState(state != State.MARRIED, "Not appropriate to marry if you are already married");
        state = State.MARRIED;
    }

    void divorce() {
        checkState(state == State.MARRIED, "You cannot divorce unless you are married");
        state = State.SINGLE;
    }

    void breakup() {
        checkState(state == State.DATING, "You cannot breakup unless you are dating");
        state = State.SINGLE;
    }

    private void checkState(boolean state, String message) {
        if (!state) {
            throw new IllegalStateException(message);
        }
    }

    public State state() {
        return state;
    }

    public enum State {
        SINGLE, DATING, MARRIED
    }
}
