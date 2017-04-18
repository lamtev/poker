package com.lamtev.poker.webserver.exception_handlers;

import com.lamtev.poker.webserver.controllers.exceptions.RoomStateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.CONFLICT;

@RestControllerAdvice
public abstract class AbstractExceptionHandler {

    ResponseMessage message(HttpStatus status, Exception e) {
        return new ResponseMessage(status.value(), e.getMessage());
    }

    @ExceptionHandler(RoomStateException.class)
    @ResponseStatus(CONFLICT)
    public ResponseMessage illegalState(RoomStateException e) {
        return message(CONFLICT, e);
    }

}
