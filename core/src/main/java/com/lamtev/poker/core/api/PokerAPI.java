package com.lamtev.poker.core.api;

import com.lamtev.poker.core.states.exceptions.*;

import java.util.List;

//TODO give only needed information to each player
public interface PokerAPI {
    void subscribe(PokerEventListener pokerEventListener);

    void setUp(List<PlayerIdStack> playersStacks,
               String dealerId,
               int smallBlindSize) throws GameOverException;

    void call() throws
            GameHaveNotBeenStartedException,
            ForbiddenMoveException,
            UnallowableMoveException,
            IsNotEnoughMoneyException,
            GameOverException;

    void raise(int additionalWager) throws
            GameHaveNotBeenStartedException,
            ForbiddenMoveException,
            UnallowableMoveException,
            IsNotEnoughMoneyException,
            NotPositiveWagerException,
            GameOverException;

    void allIn() throws
            GameHaveNotBeenStartedException,
            ForbiddenMoveException,
            UnallowableMoveException,
            GameOverException;

    void fold() throws
            UnallowableMoveException,
            GameOverException,
            GameHaveNotBeenStartedException;

    void check() throws
            GameHaveNotBeenStartedException,
            ForbiddenMoveException,
            UnallowableMoveException,
            GameOverException;

    void showDown() throws
            GameHaveNotBeenStartedException,
            ForbiddenMoveException,
            GameOverException;
}
