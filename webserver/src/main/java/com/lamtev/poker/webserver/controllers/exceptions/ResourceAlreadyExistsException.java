package com.lamtev.poker.webserver.controllers.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException(String resource) {
        super(resource);
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " already exists";
    }
}
