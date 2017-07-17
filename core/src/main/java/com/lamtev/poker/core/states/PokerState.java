package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.PlayerIdStack;
import com.lamtev.poker.core.states.exceptions.*;

import java.util.List;

public interface PokerState {

    void setUp(List<PlayerIdStack> playersInfo, String dealerId, int smallBlindSize) throws
            IllegalStateException,
            GameOverException;

    void placeBlindWagers() throws
            GameHaveNotBeenStartedException,
            ForbiddenMoveException,
            UnallowableMoveException,
            GameOverException,
            IsNotEnoughMoneyException,
            NotPositiveWagerException;

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
            GameOverException,
            IsNotEnoughMoneyException,
            NotPositiveWagerException;

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
