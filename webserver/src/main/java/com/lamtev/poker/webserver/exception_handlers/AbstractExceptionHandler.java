package com.lamtev.poker.webserver.exception_handlers;

import com.lamtev.poker.webserver.controllers.exceptions.RoomStatusException;
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

    @ExceptionHandler(RoomStatusException.class)
    @ResponseStatus(CONFLICT)
    public ResponseMessage illegalState(RoomStatusException e) {
        return message(CONFLICT, e);
    }

}
