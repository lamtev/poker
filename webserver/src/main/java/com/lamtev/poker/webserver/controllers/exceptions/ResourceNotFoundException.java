package com.lamtev.poker.webserver.controllers.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    private final String resource;

    public ResourceNotFoundException(String resource) {
        super();
        this.resource = resource;
    }

    @Override
    public String getMessage() {
        return resource + " not found";
    }

}
