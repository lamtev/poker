package com.lamtev.poker.core.states.exceptions;

public class GameHaveNotBeenStartedException extends Exception {

    private static final String MESSAGE = "Can't do action because game have not been started";

    public GameHaveNotBeenStartedException() {
        super(MESSAGE);
    }

}
