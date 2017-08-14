package com.lamtev.poker.core.exceptions;

public class RoundOfPlayIsOverException extends Exception {

    private static final String MESSAGE = "Can't do action because round of play is over";

    public RoundOfPlayIsOverException() {
        super(MESSAGE);
    }

}
