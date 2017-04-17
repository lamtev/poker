package com.lamtev.poker.core.states.exceptions;

public class UnavailableMoveException extends Exception {

    public UnavailableMoveException(String moveName) {
        super(moveName);
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " is not available.";
    }

}
