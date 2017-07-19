package com.lamtev.poker.core.api;

import com.lamtev.poker.core.states.exceptions.*;

import java.util.List;

public interface RoundOfPlay {

    void subscribe(PokerPlay pokerPlay);

    void subscribe(PokerAI pokerAI);

    void setUp(List<PlayerIdStack> playersStacks, String dealerId, int smallBlindSize) throws
            RoundOfPlayIsOverException;

    void placeBlindWagers() throws
            ForbiddenMoveException,
            GameHaveNotBeenStartedException,
            IsNotEnoughMoneyException,
            NotPositiveWagerException,
            RoundOfPlayIsOverException,
            UnallowableMoveException;

    void call() throws
            ForbiddenMoveException,
            GameHaveNotBeenStartedException,
            IsNotEnoughMoneyException,
            RoundOfPlayIsOverException,
            UnallowableMoveException;

    void raise(int additionalWager) throws
            ForbiddenMoveException,
            GameHaveNotBeenStartedException,
            IsNotEnoughMoneyException,
            NotPositiveWagerException,
            RoundOfPlayIsOverException,
            UnallowableMoveException;

    void allIn() throws
            ForbiddenMoveException,
            GameHaveNotBeenStartedException,
            IsNotEnoughMoneyException,
            NotPositiveWagerException,
            RoundOfPlayIsOverException,
            UnallowableMoveException;

    void fold() throws
            ForbiddenMoveException,
            GameHaveNotBeenStartedException,
            RoundOfPlayIsOverException,
            UnallowableMoveException;

    void check() throws
            ForbiddenMoveException,
            GameHaveNotBeenStartedException,
            RoundOfPlayIsOverException,
            UnallowableMoveException;

    void showDown() throws
            ForbiddenMoveException,
            GameHaveNotBeenStartedException,
            RoundOfPlayIsOverException;

}
