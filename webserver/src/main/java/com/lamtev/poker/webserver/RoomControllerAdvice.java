package com.lamtev.poker.webserver;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class RoomControllerAdvice {

    private final Gson gson;

    @Autowired
    public RoomControllerAdvice(Gson gson) {
        this.gson = gson;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public Error resourceNotFound(ResourceNotFoundException e) {
        return new Error(e.getMessage());
    }

}
