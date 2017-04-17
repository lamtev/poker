package com.lamtev.poker.core.states.exceptions;

public class ForbiddenMoveException extends Exception {

    private String state;

    public ForbiddenMoveException(String moveName, String state) {
        super(moveName);
        this.state = state;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " is forbidden move in " + state + ".";
    }

}
