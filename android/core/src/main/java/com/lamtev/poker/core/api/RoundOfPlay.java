package com.lamtev.poker.core.api;

import com.lamtev.poker.core.exceptions.*;

import java.util.List;

/**
 * A round of Texas Hold'em poker play.
 * The user of this interface has precise control over round of play game process.
 * <p>
 * A Poker class implements RoundOfPlay interface and it interacts with users through the Listener (Observer) pattern.
 *
 * @see Poker
 * <p>
 * To get an instance of Poker -- use create() method of PokerBuilder class.
 * @see PokerBuilder
 * @see PokerBuilder#create()
 * But first, users must subscribe to updates and configure round of play calling register and set methods.
 * @see PokerBuilder#registerPlayers(List)
 * @see PokerBuilder#registerPlay(Play)
 * @see PokerBuilder#setSmallBlindWager(int)
 * @see PokerBuilder#setDealerId(String)
 */
public interface RoundOfPlay {

    /**
     * An action which allows to place a wager equaled to current wager.
     *
     * @throws ForbiddenMoveException     if an action is forbidden on current game state.
     * @throws IsNotEnoughMoneyException  if current player haven't got enough money to place a wager.
     * @throws RoundOfPlayIsOverException if round of play is over and this action is forbidden.
     * @throws UnallowableMoveException   if there was a problem making this move in case of concrete game situation.
     */
    void call() throws
            ForbiddenMoveException,
            IsNotEnoughMoneyException,
            RoundOfPlayIsOverException,
            UnallowableMoveException;

    /**
     * An action which allows to place a wager greater than current wager by {@code additionalWager}.
     *
     * @throws ForbiddenMoveException     if an action is forbidden on current game state.
     * @throws IsNotEnoughMoneyException  if current player haven't got enough money to place a wager.
     * @throws NotPositiveWagerException  if {@code additionalWager} is not greater than 0.
     * @throws RoundOfPlayIsOverException if round of play is over and this action is forbidden.
     * @throws UnallowableMoveException   if there was a problem making this move in case of concrete game situation.
     */
    void raise(int additionalWager) throws
            ForbiddenMoveException,
            IsNotEnoughMoneyException,
            NotPositiveWagerException,
            RoundOfPlayIsOverException,
            UnallowableMoveException;

    /**
     * An action which allows to place a wager equaled to player stack.
     *
     * @throws ForbiddenMoveException     if an action is forbidden on current game state.
     * @throws RoundOfPlayIsOverException if round of play is over and this action is forbidden.
     * @throws UnallowableMoveException   if there was a problem making this move in case of concrete game situation.
     */
    void allIn() throws
            ForbiddenMoveException,
            RoundOfPlayIsOverException,
            UnallowableMoveException;

    /**
     * An action which allows to fold cards and skip current round of play
     *
     * @throws ForbiddenMoveException     if an action is forbidden on current game state.
     * @throws RoundOfPlayIsOverException if round of play is over and this action is forbidden.
     * @throws UnallowableMoveException   if there was a problem making this move in case of concrete game situation.
     */
    void fold() throws
            ForbiddenMoveException,
            RoundOfPlayIsOverException,
            UnallowableMoveException;

    /**
     * An action which allows to move on without placing a wager or folding cards.
     *
     * @throws ForbiddenMoveException     if an action is forbidden on current game state.
     * @throws RoundOfPlayIsOverException if round of play is over and this action is forbidden.
     * @throws UnallowableMoveException   if there was a problem making this move in case of concrete game situation.
     */
    void check() throws
            ForbiddenMoveException,
            RoundOfPlayIsOverException,
            UnallowableMoveException;

    /**
     * An action which allows to show cards to other players to compare hands.
     *
     * @throws ForbiddenMoveException     if an action is forbidden on current game state.
     * @throws RoundOfPlayIsOverException if round of play is over and this action is forbidden.
     */
    void showDown() throws
            ForbiddenMoveException,
            RoundOfPlayIsOverException;

}
