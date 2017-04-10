package com.lamtev.poker.webserver;

public class ResourceNotFoundException extends RuntimeException {

    private String resource;

    public ResourceNotFoundException(String resource) {
        this.resource = resource;
    }

    public String getMessage() {
        return resource;
    }

}
