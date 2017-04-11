package com.lamtev.poker.webserver.controllers.exceptions;

public class RequestBodyHasUnsuitableFormatException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Response body has unsuitable format";
    }
}
