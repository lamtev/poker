package com.lamtev.poker.webserver;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestControllerAdvice
public class RoomControllerAdvice {

    private final Gson gson;

    @Autowired
    public RoomControllerAdvice(Gson gson) {
        this.gson = gson;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(NO_CONTENT)
    public Error resourceNotFound(ResourceNotFoundException e) {
        String resource = e.getMessage();
        return new Error(resource + " not found");
    }

}
