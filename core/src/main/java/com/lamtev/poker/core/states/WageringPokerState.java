package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.PlayerMoney;
import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.*;

import java.util.ArrayList;
import java.util.List;

abstract class WageringPokerState extends ActionPokerState {

    private MoveValidator moveValidator;
    private int checks = 0;
    private List<Player> raisers = new ArrayList<>();

    WageringPokerState(Poker poker, Players players, Bank bank, Dealer dealer, Cards commonCards, int bigBlindIndex) {
        super(poker, players, bank, dealer, commonCards, bigBlindIndex);
        determinePlayerIndex(bigBlindIndex);
        poker.notifyCurrentPlayerListeners(currentPlayer().getId());
        moveValidator = new MoveValidator(players, bank);
    }

    WageringPokerState(ActionPokerState state) {
        super(state);
        determinePlayerIndex(bigBlindIndex);
        poker.notifyCurrentPlayerListeners(currentPlayer().getId());
        moveValidator = new MoveValidator(players, bank);
    }

    @Override
    public void call() throws Exception {
        moveValidator.validateCall(currentPlayer());
        bank.acceptCallFromPlayer(currentPlayer());
        wagerPlaced();
        changePlayerIndex();
        attemptNextState();
    }

    @Override
    public void raise(int additionalWager) throws Exception {
        moveValidator.validateRaise(raisers.size());
        bank.acceptRaiseFromPlayer(additionalWager, currentPlayer());
        raisers.add(currentPlayer());
        wagerPlaced();
        changePlayerIndex();
    }

    @Override
    public void allIn() throws Exception {
        int additionalWager = currentPlayer().getStack() - (bank.getCurrentWager() - currentPlayer().getWager());

        if (additionalWager == 0) {
            call();
            System.out.println("call");
        } else if (additionalWager > 0) {
            raise(additionalWager);
        } else {
            bank.acceptAllInFromPlayer(currentPlayer());
            wagerPlaced();
            changePlayerIndex();
            attemptNextState();
        }
    }

    @Override
    public void fold() throws Exception {
        currentPlayer().fold();
        poker.notifyPlayerFoldListeners(currentPlayer().getId());
        changePlayerIndex();
        if (isOnlyOneActivePlayer()) {
            bank.giveMoneyToWinners(currentPlayer());
            gameIsOverState();
            return;
        }
        attemptNextState();
    }

    @Override
    public void check() throws Exception {
        moveValidator.validateCheck(raisers.size());
        changePlayerIndex();
        ++checks;
        attemptNextState();
    }

    @Override
    public void showDown() throws Exception {
        throw new Exception("Can't show down when wagering");
    }

    private void wagerPlaced() {
        String playerId = currentPlayer().getId();
        int stack = currentPlayer().getStack();
        int wager = currentPlayer().getWager();
        int bank = this.bank.getMoney();
        PlayerMoney playerMoney = new PlayerMoney(stack, wager);
        poker.notifyWagerPlacedListeners(playerId, playerMoney, bank);
    }

    private int numberOfNotAllInnersActivePlayersWithSameWagers() {
        int count = 0;
        for (Player player : players) {
            if (player.isActive() && !bank.getAllInners().contains(player) &&
                    player.getWager() == bank.getCurrentWager()) {
                ++count;
            }
        }
        return count;
    }

    private boolean isOnlyOneActivePlayer() {
        return players.activePlayersNumber() == 1;
    }

    private void gameIsOverState() throws Exception {
        poker.setState(new GameIsOverPokerState(this));
    }

    boolean timeToNextState() {
        return checks == players.activePlayersNumber() ||
                numberOfNotAllInnersActivePlayersWithSameWagers() + bank.getAllInners().size()
                        == players.activePlayersNumber() && raisers.size() > 0;
    }

    int latestAggressorIndex() {
        if (raisers.size() == 0) {
            return playerIndex;
        }
        for (int i = raisers.size() - 1; i >= 0; ++i) {
            if (raisers.get(i).isActive()) {
                return players.indexOf(raisers.get(i));
            }
        }

        int i = 0;
        for (Player player : players) {
            if (player.isActive()) {
                return players.indexOf(raisers.get(i));
            }
            ++i;
        }
        return -1;
    }

    protected abstract void attemptNextState() throws Exception;

    boolean timeToShowDown() {
        return timeToNextState() && bank.getAllInners().size() != 0;
    }

    boolean preflopWageringHasBeenFinished() {
        return numberOfNotAllInnersActivePlayersWithSameWagers() + bank.getAllInners().size() == players.activePlayersNumber();
    }

    private void determinePlayerIndex(int bigBlindIndex) {
        playerIndex = bigBlindIndex;
        changePlayerIndex();
    }

}
