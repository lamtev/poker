package com.lamtev.poker.core.exceptions;

public class NotPositiveWagerException extends Exception {

    private static final String MESSAGE = "Wager can not be negative";

    public NotPositiveWagerException() {
        super(MESSAGE);
    }
}
