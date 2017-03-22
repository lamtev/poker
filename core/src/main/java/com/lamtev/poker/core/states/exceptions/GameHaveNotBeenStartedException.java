package com.lamtev.poker.core.states.exceptions;

public class GameHaveNotBeenStartedException extends IllegalStateException {

    private static final String MESSAGE = "Can't do action because game have not been started";

    public GameHaveNotBeenStartedException() {
        super(MESSAGE);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
