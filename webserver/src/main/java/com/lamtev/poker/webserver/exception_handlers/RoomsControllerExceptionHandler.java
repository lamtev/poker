package com.lamtev.poker.webserver.exception_handlers;

import com.lamtev.poker.core.states.exceptions.GameOverException;
import com.lamtev.poker.webserver.controllers.exceptions.RequestBodyHasUnsuitableFormatException;
import com.lamtev.poker.webserver.controllers.exceptions.ResourceAlreadyExistsException;
import com.lamtev.poker.webserver.controllers.exceptions.ResourceNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class RoomsControllerExceptionHandler extends AbstractExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ResponseMessage resourceNotFound(ResourceNotFoundException e) {
        return message(NOT_FOUND, e);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(CONFLICT)
    public ResponseMessage resourceAlreadyExists(ResourceAlreadyExistsException e) {
        return message(CONFLICT, e);
    }

    @ExceptionHandler(RequestBodyHasUnsuitableFormatException.class)
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    public ResponseMessage requestBodyHasUnsuitableFormat(RequestBodyHasUnsuitableFormatException e) {
        return message(UNPROCESSABLE_ENTITY, e);
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(CONFLICT)
    public ResponseMessage illegalState(IllegalStateException e) {
        return message(CONFLICT, e);
    }

    //TODO replace BAD_REQUEST by better status
    @ExceptionHandler(GameOverException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseMessage gameOver(GameOverException e) {
        return message(BAD_REQUEST, e);
    }

}
