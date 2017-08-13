package com.lamtev.poker.core.states;

import com.lamtev.poker.core.exceptions.*;

public interface PokerState {

    void start();

    void call() throws
            ForbiddenMoveException,
            UnallowableMoveException,
            IsNotEnoughMoneyException,
            RoundOfPlayIsOverException;

    void raise(int additionalWager) throws
            ForbiddenMoveException,
            UnallowableMoveException,
            IsNotEnoughMoneyException,
            NotPositiveWagerException,
            RoundOfPlayIsOverException;

    void allIn() throws
            ForbiddenMoveException,
            UnallowableMoveException,
            RoundOfPlayIsOverException;

    void fold() throws
            ForbiddenMoveException,
            UnallowableMoveException,
            RoundOfPlayIsOverException;

    void check() throws
            ForbiddenMoveException,
            UnallowableMoveException,
            RoundOfPlayIsOverException;

    void showDown() throws
            ForbiddenMoveException,
            RoundOfPlayIsOverException;

}
