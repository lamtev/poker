package com.lamtev.poker.core.states;

import com.lamtev.poker.core.model.Player;
import com.lamtev.poker.core.states.exceptions.ForbiddenMoveException;
import com.lamtev.poker.core.states.exceptions.IsNotEnoughMoneyException;
import com.lamtev.poker.core.states.exceptions.NotPositiveWagerException;
import com.lamtev.poker.core.states.exceptions.UnallowableMoveException;

import java.util.ArrayList;
import java.util.List;

abstract class WageringState extends ActionState {

    private final MoveValidator moveValidator;
    private int checks = 0;
    private final List<Player> raisers = new ArrayList<>();

    WageringState(ActionState state) {
        super(state);
        determineUnderTheGunPosition();
        poker().notifyCurrentPlayerChangedListeners(players().current().id());
        moveValidator = new MoveValidator(players(), bank());
        moveAbility().setAllInIsAble(true);
    }

    @Override
    public void placeBlindWagers() throws ForbiddenMoveException {
        throw new ForbiddenMoveException("Placing blind wagers", toString());
    }

    @Override
    public void call() throws UnallowableMoveException, IsNotEnoughMoneyException {
        Player currentPlayer = players().current();
        moveValidator.validateCall(currentPlayer);
        bank().acceptCall(currentPlayer);
        notifyMoneyUpdatedListeners();
        poker().notifyPlayerCalledListeners(currentPlayer.id());
        changePlayerIndex();
        attemptNextState();
    }

    @Override
    public void raise(int additionalWager) throws UnallowableMoveException,
            IsNotEnoughMoneyException, NotPositiveWagerException {
        moveValidator.validateRaise(raisers.size());
        Player currentPlayer = players().current();
        bank().acceptRaise(additionalWager, currentPlayer);
        raisers.add(currentPlayer);
        notifyMoneyUpdatedListeners();
        poker().notifyPlayerRaisedListeners(currentPlayer.id());
        changePlayerIndex();
    }

    @Override
    public void allIn() throws UnallowableMoveException,
            IsNotEnoughMoneyException, NotPositiveWagerException {
        Player currentPlayer = players().current();
        int additionalWager = currentPlayer.stack() -
                (bank().wager() - currentPlayer.wager());
        if (additionalWager == 0) {
            call();
        } else if (additionalWager > 0) {
            raise(additionalWager);
        } else {
            bank().acceptAllIn(players().current());
            notifyMoneyUpdatedListeners();
            poker().notifyPlayerAllinnedListeners(currentPlayer.id());
            changePlayerIndex();
            attemptNextState();
        }
    }

    @Override
    public void fold() throws UnallowableMoveException {
        Player currentPlayer = players().current();
        currentPlayer.fold();
        poker().notifyPlayerFoldListeners(currentPlayer.id());
        changePlayerIndex();
        if (onlyOneActivePlayer()) {
            Player winner = players().nextActive();
            bank().giveMoneyToSingleWinner(winner);
            poker().notifyPlayerMoneyUpdatedListeners(
                    winner.id(),
                    winner.stack(), winner.wager()
            );
            poker().setState(new RoundOfPlayIsOverState(this));
            return;
        }
        attemptNextState();
    }

    @Override
    public void check() throws ForbiddenMoveException, UnallowableMoveException {
        moveValidator.validateCheck(raisers.size());
        poker().notifyPlayerCheckedListeners(players().current().id());
        ++checks;
        changePlayerIndex();
        attemptNextState();
    }

    @Override
    public void showDown() throws ForbiddenMoveException {
        throw new ForbiddenMoveException("Show down", toString());
    }

    abstract void determineUnderTheGunPosition();

    abstract void attemptNextState();

    private void notifyMoneyUpdatedListeners() {
        Player currentPlayer = players().current();
        poker().notifyPlayerMoneyUpdatedListeners(currentPlayer.id(), currentPlayer.stack(), currentPlayer.wager());
        poker().notifyBankMoneyUpdatedListeners(bank().money(), bank().wager());
    }

    boolean timeToNextState() {
        return allActiveNonAllinnersChecked() ||
                thereWereRaisesAndAllActivePlayersAreAllinnersOrHaveSameWagers();
    }

    Player latestAggressor() {
        return raisers.stream()
                .filter(Player::isActive)
                .reduce((first, second) -> second)
                .orElse(null);
    }

    int checks() {
        return checks;
    }

    List<Player> raisers() {
        return raisers;
    }

    private boolean onlyOneActivePlayer() {
        return players().activePlayersNumber() == 1;
    }

    private boolean allActiveNonAllinnersChecked() {
        return checks == players().activeNonAllinnersNumber();
    }

    private boolean thereWereRaisesAndAllActivePlayersAreAllinnersOrHaveSameWagers() {
        return !raisers.isEmpty() && players().activeNonAllinnersWithSameWagerNumber(bank().wager())
                + players().allinnersNumber() == players().activePlayersNumber();
    }

}
