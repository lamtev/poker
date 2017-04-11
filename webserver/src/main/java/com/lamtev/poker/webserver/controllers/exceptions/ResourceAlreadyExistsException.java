package com.lamtev.poker.webserver.controllers.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException {

    private final String resource;

    public ResourceAlreadyExistsException(String resource) {
        this.resource = resource;
    }

    @Override
    public String getMessage() {
        return resource + " already exists";
    }
}
