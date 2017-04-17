package com.lamtev.poker.webserver.exception_handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public abstract class AbstractExceptionHandler {

    ResponseMessage message(HttpStatus status, Exception e) {
        return new ResponseMessage(status.value(), e.getMessage());
    }

}
