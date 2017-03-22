package com.lamtev.poker.core.states.exceptions;

public class GameOverException extends IllegalStateException {

    private static final String MESSAGE = "Can't do action because game is over";

    public GameOverException() {
        super(MESSAGE);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
