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
    }

    @Override
    public void placeBlindWagers() throws ForbiddenMoveException {
        throw new ForbiddenMoveException("Placing blind wagers", toString());
    }

    @Override
    public void call() throws UnallowableMoveException, IsNotEnoughMoneyException {
        moveValidator.validateCall(players().current());
        bank().acceptCall(players().current());
        wagerPlaced();
        changePlayerIndex();
        attemptNextState();
    }

    @Override
    public void raise(int additionalWager) throws UnallowableMoveException,
            IsNotEnoughMoneyException, NotPositiveWagerException {
        moveValidator.validateRaise(raisers.size());
        bank().acceptRaise(additionalWager, players().current());
        raisers.add(players().current());
        wagerPlaced();
        changePlayerIndex();
    }

    @Override
    public void allIn() throws UnallowableMoveException,
            IsNotEnoughMoneyException, NotPositiveWagerException {
        int additionalWager = players().current().stack() -
                (bank().currentWager() - players().current().wager());
        if (additionalWager == 0) {
            call();
        } else if (additionalWager > 0) {
            raise(additionalWager);
        } else {
            bank().acceptAllIn(players().current());
            wagerPlaced();
            changePlayerIndex();
            attemptNextState();
        }
    }

    @Override
    public void fold() throws UnallowableMoveException {
        players().current().fold();
        poker().notifyPlayerFoldListeners(players().current().id());
        changePlayerIndex();
        if (onlyOneActivePlayer()) {
            Player winner = players().nextActive();
            bank().giveMoneyToSingleWinner(winner);
            poker().notifyPlayerMoneyUpdatedListeners(
                    winner.id(),
                    winner.stack(), winner.wager()
            );
            gameIsOverState();
            return;
        }
        attemptNextState();
    }

    @Override
    public void check() throws ForbiddenMoveException, UnallowableMoveException {
        moveValidator.validateCheck(raisers.size());
        changePlayerIndex();
        ++checks;
        attemptNextState();
    }

    @Override
    public void showDown() throws ForbiddenMoveException {
        throw new ForbiddenMoveException("Show down", toString());
    }

    abstract void determineUnderTheGunPosition();

    abstract void attemptNextState();

    private void wagerPlaced() {
        String playerId = players().current().id();
        int stack = players().current().stack();
        int wager = players().current().wager();
        int bank = this.bank().money();
        poker().notifyPlayerMoneyUpdatedListeners(playerId, stack, wager);
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

    private void gameIsOverState() {
        poker().setState(new RoundOfPlayIsOverState(this));
    }

    private boolean allActiveNonAllinnersChecked() {
        return checks == players().activeNonAllinnersNumber();
    }

    private boolean thereWereRaisesAndAllActivePlayersAreAllinnersOrHaveSameWagers() {
        return !raisers.isEmpty() && players().activeNonAllinnersWithSameWagerNumber(bank().currentWager())
                + players().allinnersNumber() == players().activePlayersNumber();
    }

}
