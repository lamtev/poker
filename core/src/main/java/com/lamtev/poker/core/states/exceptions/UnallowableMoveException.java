package com.lamtev.poker.core.states.exceptions;

public class UnallowableMoveException extends Exception {

    private final static String IS_NOT_ALLOWABLE = " is not allowable";

    public UnallowableMoveException(String moveName) {
        super(moveName + IS_NOT_ALLOWABLE);
    }
}
