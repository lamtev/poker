package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.PlayerMoney;
import com.lamtev.poker.core.model.Player;
import com.lamtev.poker.core.states.exceptions.ForbiddenMoveException;
import com.lamtev.poker.core.states.exceptions.IsNotEnoughMoneyException;
import com.lamtev.poker.core.states.exceptions.NotPositiveWagerException;
import com.lamtev.poker.core.states.exceptions.UnallowableMoveException;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

abstract class WageringPokerState extends ActionPokerState {

    private MoveValidator moveValidator;
    private int checks = 0;
    private List<Player> raisers = new ArrayList<>();

    WageringPokerState(ActionPokerState state) {
        super(state);
        determineUnderTheGunPosition();
        poker().notifyCurrentPlayerChangedListeners(players().current().getId());
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
        int additionalWager = players().current().getStack() -
                (bank().getCurrentWager() - players().current().getWager());

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
        poker().notifyPlayerFoldListeners(players().current().getId());
        changePlayerIndex();
        if (isOnlyOneActivePlayer()) {
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
        String playerId = players().current().getId();
        int stack = players().current().getStack();
        int wager = players().current().getWager();
        int bank = this.bank().getMoney();
        PlayerMoney playerMoney = new PlayerMoney(stack, wager);
        poker().notifyWagerPlacedListeners(playerId, playerMoney, bank);
    }

    boolean timeToNextState() {
        return checks == players().activePlayersNumber() ||
                numberOfNotAllInnersActivePlayersWithSameWagers() + bank().getAllInners().size()
                        == players().activePlayersNumber() && raisers.size() > 0 ;
    }

    Player latestAggressor() {
        ListIterator<Player> it = raisers.listIterator();
        while (it.hasPrevious()) {
            Player latestAggressor = it.previous();
            if (latestAggressor.isActive()) {
                return latestAggressor;
            }
        }
        return null;
    }

    //TODO delegate to Players
    int numberOfNotAllInnersActivePlayersWithSameWagers() {
        int count = 0;
        for (Player player : players()) {
            if (player.isActive() && !bank().getAllInners().contains(player) &&
                    player.getWager() == bank().getCurrentWager()) {
                ++count;
            }
        }
        return count;
    }

    int checks() {
        return checks;
    }

    List<Player> raisers() {
        return raisers;
    }

    private boolean isOnlyOneActivePlayer() {
        return players().activePlayersNumber() == 1;
    }

    private void gameIsOverState() {
        poker().setState(new GameIsOverPokerState(this));
    }

}
