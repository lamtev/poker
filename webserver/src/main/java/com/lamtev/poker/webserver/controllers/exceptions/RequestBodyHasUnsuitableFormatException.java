package com.lamtev.poker.webserver.controllers.exceptions;

public class RequestBodyHasUnsuitableFormatException extends RuntimeException {

    private static final String MESSAGE = "Response body has unsuitable format";

    public RequestBodyHasUnsuitableFormatException() {
        super(MESSAGE);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
