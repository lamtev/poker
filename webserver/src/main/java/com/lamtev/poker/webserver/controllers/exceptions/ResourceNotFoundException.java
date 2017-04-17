package com.lamtev.poker.webserver.controllers.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resource) {
        super(resource);
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " not found";
    }

}
