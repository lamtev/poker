package com.lamtev.poker.core.states.exceptions;

public class GameIsOverException extends IllegalStateException {

    private static String MESSAGE = "Can't do action because game is over";

    public GameIsOverException() {
        super(MESSAGE);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
