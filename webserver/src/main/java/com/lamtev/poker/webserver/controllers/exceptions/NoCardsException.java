package com.lamtev.poker.webserver.controllers.exceptions;

public class NoCardsException extends Exception {

    private final static String MESSAGE = "No cards";

    public NoCardsException() {
        super(MESSAGE);
    }

}
