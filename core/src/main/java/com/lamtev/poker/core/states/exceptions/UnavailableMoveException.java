package com.lamtev.poker.core.states.exceptions;

public class UnavailableMoveException extends Exception {

    private final static String IS_NOT_AVAILABLE = " is not available";

    public UnavailableMoveException(String moveName) {
        super(moveName + IS_NOT_AVAILABLE);
    }
}
