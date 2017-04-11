package com.lamtev.poker.webserver;

public class ResourceNotFoundException extends RuntimeException {

    private String resource;

    public ResourceNotFoundException(String resource) {
        super();
        this.resource = resource;
    }

    @Override
    public String getMessage() {
        return resource + " not found";
    }

}
