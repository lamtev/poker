package com.lamtev.poker.core.api;

import com.lamtev.poker.core.states.exceptions.*;

import java.util.List;

//TODO give only needed information to each player
public interface RoundOfPlay {
    void subscribe(PokerAI listener);

    void subscribe(PokerPlayer listener);

    void subscribe(PokerPlay listener);

    void setUp(List<PlayerIdStack> playersStacks,
               String dealerId,
               int smallBlindSize) throws RoundOfPlayIsOverException;

    void placeBlindWagers() throws
            NotPositiveWagerException,
            ForbiddenMoveException,
            IsNotEnoughMoneyException,
            GameHaveNotBeenStartedException,
            RoundOfPlayIsOverException,
            UnallowableMoveException;

    void call() throws
            GameHaveNotBeenStartedException,
            ForbiddenMoveException,
            UnallowableMoveException,
            IsNotEnoughMoneyException,
            RoundOfPlayIsOverException;

    void raise(int additionalWager) throws
            GameHaveNotBeenStartedException,
            ForbiddenMoveException,
            UnallowableMoveException,
            IsNotEnoughMoneyException,
            NotPositiveWagerException,
            RoundOfPlayIsOverException;

    void allIn() throws
            GameHaveNotBeenStartedException,
            ForbiddenMoveException,
            UnallowableMoveException,
            RoundOfPlayIsOverException,
            IsNotEnoughMoneyException,
            NotPositiveWagerException;

    void fold() throws
            UnallowableMoveException,
            RoundOfPlayIsOverException,
            GameHaveNotBeenStartedException;

    void check() throws
            GameHaveNotBeenStartedException,
            ForbiddenMoveException,
            UnallowableMoveException,
            RoundOfPlayIsOverException;

    void showDown() throws
            GameHaveNotBeenStartedException,
            ForbiddenMoveException,
            RoundOfPlayIsOverException;
}
