package com.lamtev.poker.core.states.exceptions;

public class ForbiddenMoveException extends Exception {

    private final static String IS_FORBIDDEN_MOVE_IN = " is forbidden move in ";

    public ForbiddenMoveException(String moveName, String state) {
        super(moveName + IS_FORBIDDEN_MOVE_IN + state);
    }

}
