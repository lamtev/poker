package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.PlayerMoney;
import com.lamtev.poker.core.model.Player;
import com.lamtev.poker.core.states.exceptions.ForbiddenMoveException;
import com.lamtev.poker.core.states.exceptions.IsNotEnoughMoneyException;
import com.lamtev.poker.core.states.exceptions.NotPositiveWagerException;
import com.lamtev.poker.core.states.exceptions.UnallowableMoveException;

import java.util.ArrayList;
import java.util.List;

abstract class WageringPokerState extends ActionPokerState {

    private MoveValidator moveValidator;
    private int checks = 0;
    private List<Player> raisers = new ArrayList<>();

    WageringPokerState(ActionPokerState state) {
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
        bank().acceptCallFromPlayer(players().current());
        wagerPlaced();
        changePlayerIndex();
        attemptNextState();
    }

    @Override
    public void raise(int additionalWager) throws UnallowableMoveException,
            IsNotEnoughMoneyException, NotPositiveWagerException {
        moveValidator.validateRaise(raisers.size());
        bank().acceptRaiseFromPlayer(additionalWager, players().current());
        raisers.add(players().current());
        wagerPlaced();
        changePlayerIndex();
    }

    @Override
    public void allIn() throws UnallowableMoveException,
            IsNotEnoughMoneyException, NotPositiveWagerException {
        //FIXME
        int additionalWager = players().current().stack() -
                (bank().currentWager() - players().current().wager());

        if (additionalWager == 0) {
            call();
        } else if (additionalWager > 0) {
            raise(additionalWager);
        } else {
            bank().acceptAllInFromPlayer(players().current());
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
            bank().giveMoneyToWinners(players().current());
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
        PlayerMoney playerMoney = new PlayerMoney(stack, wager);
        poker().notifyWagerPlacedListeners(playerId, playerMoney, bank);
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
        poker().setState(new GameIsOverPokerState(this));
    }

    private boolean allActiveNonAllinnersChecked() {
        return checks == players().activeNonAllinnersNumber();
    }

    private boolean thereWereRaisesAndAllActivePlayersAreAllinnersOrHaveSameWagers() {
        return !raisers.isEmpty() && players().activeNonAllinnersWithSameWagerNumber(bank().currentWager())
                + players().allinnersNumber() == players().activePlayersNumber();
    }

}
