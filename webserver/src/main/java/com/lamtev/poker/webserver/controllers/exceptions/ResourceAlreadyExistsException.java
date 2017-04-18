package com.lamtev.poker.webserver.controllers.exceptions;

public class ResourceAlreadyExistsException extends Exception {

    private static final String ALREADY_EXISTS = " already exists";

    public ResourceAlreadyExistsException(String resource) {
        super(resource + ALREADY_EXISTS);
    }

}
