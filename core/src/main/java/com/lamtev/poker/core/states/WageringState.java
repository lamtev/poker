package com.lamtev.poker.core.states;

import com.lamtev.poker.core.model.Player;
import com.lamtev.poker.core.states.exceptions.ForbiddenMoveException;
import com.lamtev.poker.core.states.exceptions.IsNotEnoughMoneyException;
import com.lamtev.poker.core.states.exceptions.NotPositiveWagerException;
import com.lamtev.poker.core.states.exceptions.UnallowableMoveException;

import java.util.ArrayList;
import java.util.List;

abstract class WageringState extends ActionState {

    private final List<Player> raisers = new ArrayList<>();
    private final MoveValidator moveValidator;
    private int checks = 0;

    WageringState(ActionState state) {
        super(state);
        moveValidator = new MoveValidator(players, bank);
    }

    @Override
    public void start() {
        determineUnderTheGunPosition();
        moveAbility.setAllInIsAble(true);
        moveAbility.setFoldIsAble(true);
        updateMoveAbility();
        makeDealerJob();
        poker.notifyCurrentPlayerChangedListeners(players.current().id());
    }

    @Override
    boolean timeToForcedShowdown() {
        return players.activeNonAllinnersNumber() == 0;
    }

    @Override
    public void call() throws UnallowableMoveException, IsNotEnoughMoneyException {
        Player currentPlayer = players.current();
        moveValidator.validateCall();
        bank.acceptCall(currentPlayer);
        notifyMoneyUpdatedListeners();
        poker.notifyPlayerCalledListeners(currentPlayer.id());
        boolean stateChanged = attemptNextState();
        if (!stateChanged) {
            changePlayerIndex();
        }
    }

    @Override
    public void raise(int additionalWager) throws UnallowableMoveException,
            IsNotEnoughMoneyException, NotPositiveWagerException {
        moveValidator.validateRaise(raisers.size());
        Player currentPlayer = players.current();
        bank.acceptRaise(additionalWager, currentPlayer);
        raisers.add(currentPlayer);
        notifyMoneyUpdatedListeners();
        poker.notifyPlayerRaisedListeners(currentPlayer.id());
        changePlayerIndex();
    }

    @Override
    public void allIn() throws UnallowableMoveException,
            IsNotEnoughMoneyException, NotPositiveWagerException {
        Player currentPlayer = players.current();
        int additionalWager = currentPlayer.stack() -
                (bank.wager() - currentPlayer.wager());
        if (additionalWager == 0) {
            call();
        } else if (additionalWager > 0) {
            raise(additionalWager);
        } else {
            bank.acceptAllIn(players.current());
            notifyMoneyUpdatedListeners();
            poker.notifyPlayerAllinnedListeners(currentPlayer.id());
            boolean stateChanged = attemptNextState();
            if (!stateChanged) {
                changePlayerIndex();
            }
        }
    }

    @Override
    public void fold() throws UnallowableMoveException {
        Player currentPlayer = players.current();
        currentPlayer.fold();
        poker.notifyPlayerFoldListeners(currentPlayer.id());
        if (onlyOneActivePlayer()) {
            Player winner = players.nextActive();
            bank.giveMoneyToSingleWinner(winner);
            poker.notifyPlayerMoneyUpdatedListeners(
                    winner.id(),
                    winner.stack(), winner.wager()
            );
            poker.setState(new RoundOfPlayIsOverState());
            return;
        }
        boolean stateChanged = attemptNextState();
        if (!stateChanged) {
            changePlayerIndex();
        }
    }

    @Override
    public void check() throws ForbiddenMoveException, UnallowableMoveException {
        moveValidator.validateCheck(raisers.size());
        poker.notifyPlayerCheckedListeners(players.current().id());
        ++checks;
        boolean stateChanged = attemptNextState();
        if (!stateChanged) {
            changePlayerIndex();
        }
    }

    @Override
    public void showDown() throws ForbiddenMoveException {
        throw new ForbiddenMoveException("Show down", toString());
    }

    //TODO think about all in ability if all in is raise

    @Override
    void updateMoveAbility() {
        moveAbility.setRaiseIsAble(raiseIsAble());
        moveAbility.setCallIsAble(moveValidator.callIsAble());
        moveAbility.setCheckIsAble(moveValidator.checkIsAble(raisers.size()));
        poker.notifyMoveAbilityListeners(players.current().id(), moveAbility);
    }

    final boolean raiseIsAble() {
        return moveValidator.raiseIsAble(raisers.size());
    }

    void determineUnderTheGunPosition() {
        players.nextNonAllinnerAfterDealer();
    }

    abstract void makeDealerJob();

    abstract boolean attemptNextState();

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
        return players.activePlayersNumber() == 1;
    }

    private boolean allActiveNonAllinnersChecked() {
        return checks == players.activeNonAllinnersNumber();
    }

    private boolean thereWereRaisesAndAllActivePlayersAreAllinnersOrHaveSameWagers() {
        return !raisers.isEmpty() && players.activeNonAllinnersWithSameWagerNumber(bank.wager())
                + players.allinnersNumber() == players.activePlayersNumber();
    }

    private void notifyMoneyUpdatedListeners() {
        Player currentPlayer = players.current();
        poker.notifyPlayerMoneyUpdatedListeners(currentPlayer.id(), currentPlayer.stack(), currentPlayer.wager());
        poker.notifyBankMoneyUpdatedListeners(bank.money(), bank.wager());
    }

}
