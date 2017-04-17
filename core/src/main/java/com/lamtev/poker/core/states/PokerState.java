package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.PlayerIdStack;
import com.lamtev.poker.core.states.exceptions.*;

import java.util.List;

public interface PokerState {

    void setUp(List<PlayerIdStack> playersInfo, String smallBlindId, String bigBlindId, int smallBlindSize) throws
            IllegalStateException,
            GameOverException;

    void call() throws
            GameHaveNotBeenStartedException,
            ForbiddenMoveException,
            UnavailableMoveException,
            IsNotEnoughMoneyException,
            GameOverException;

    void raise(int additionalWager) throws
            GameHaveNotBeenStartedException,
            ForbiddenMoveException,
            UnavailableMoveException,
            IsNotEnoughMoneyException,
            GameOverException;

    void allIn() throws
            GameHaveNotBeenStartedException,
            ForbiddenMoveException,
            UnavailableMoveException,
            GameOverException;

    void fold() throws Exception;

    void check() throws
            GameHaveNotBeenStartedException,
            ForbiddenMoveException,
            UnavailableMoveException,
            GameOverException;

    void showDown() throws
            GameHaveNotBeenStartedException,
            ForbiddenMoveException,
            GameOverException;

}
