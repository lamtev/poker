package com.lamtev.poker.webserver.exception_handlers;

import com.lamtev.poker.core.states.exceptions.ForbiddenMoveException;
import com.lamtev.poker.core.states.exceptions.IsNotEnoughMoneyException;
import com.lamtev.poker.core.states.exceptions.NotPositiveWagerException;
import com.lamtev.poker.core.states.exceptions.UnallowableMoveException;
import com.lamtev.poker.webserver.controllers.exceptions.NoCardsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GameControllerExceptionHandler extends AbstractExceptionHandler {

    //TODO think about http statuses

    @ExceptionHandler(ForbiddenMoveException.class)
    @ResponseStatus(FORBIDDEN)
    public ResponseMessage forbiddenMove(ForbiddenMoveException e) {
        return message(FORBIDDEN, e);
    }

    @ExceptionHandler(UnallowableMoveException.class)
    @ResponseStatus(FORBIDDEN)
    public ResponseMessage unallowableMove(UnallowableMoveException e) {
        return message(FORBIDDEN, e);
    }

    @ExceptionHandler(IsNotEnoughMoneyException.class)
    @ResponseStatus(FORBIDDEN)
    public ResponseMessage isNotEnoughMoney(IsNotEnoughMoneyException e) {
        return message(FORBIDDEN, e);
    }

    @ExceptionHandler(NotPositiveWagerException.class)
    @ResponseStatus(FORBIDDEN)
    public ResponseMessage notPositiveWager(NotPositiveWagerException e) {
        return message(FORBIDDEN, e);
    }

    @ExceptionHandler(NoCardsException.class)
    @ResponseStatus(NOT_FOUND)
    public ResponseMessage noCards(NoCardsException e) {
        return message(NOT_FOUND, e);
    }

}
