package com.lamtev.poker.webserver.controllers.exceptions;

public class RequestBodyHasUnsuitableFormatException extends Exception {

    private static final String MESSAGE = "Request body has unsuitable format";

    public RequestBodyHasUnsuitableFormatException() {
        super(MESSAGE);
    }

}
