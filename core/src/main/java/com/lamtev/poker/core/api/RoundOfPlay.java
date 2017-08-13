package com.lamtev.poker.core.api;

import com.lamtev.poker.core.exceptions.*;

/**
 * A round of Texas Hold'em poker play.
 * The user of this interface has precise control over round of play game process.
 */
public interface RoundOfPlay {

    /**
     * A poker action which allows to place a wager equaled to current wager.
     *
     * @throws ForbiddenMoveException     when the action is forbidden on current game state.
     * @throws IsNotEnoughMoneyException  x
     * @throws RoundOfPlayIsOverException x
     * @throws UnallowableMoveException   x
     */
    void call() throws
            ForbiddenMoveException,
            IsNotEnoughMoneyException,
            RoundOfPlayIsOverException,
            UnallowableMoveException;

    void raise(int additionalWager) throws
            ForbiddenMoveException,
            IsNotEnoughMoneyException,
            NotPositiveWagerException,
            RoundOfPlayIsOverException,
            UnallowableMoveException;

    void allIn() throws
            ForbiddenMoveException,
            RoundOfPlayIsOverException,
            UnallowableMoveException;

    void fold() throws
            ForbiddenMoveException,
            RoundOfPlayIsOverException,
            UnallowableMoveException;

    void check() throws
            ForbiddenMoveException,
            RoundOfPlayIsOverException,
            UnallowableMoveException;

    void showDown() throws
            ForbiddenMoveException,
            RoundOfPlayIsOverException;

}
