package com.lamtev.poker.core.model;

import com.lamtev.poker.core.states.exceptions.IsNotEnoughMoneyException;
import com.lamtev.poker.core.states.exceptions.NotPositiveWagerException;

import java.util.List;

public final class Bank {

    //TODO side pots
    private Pot mainPot = new Pot();
    private Players players;

    public Bank(Players players) {
        this.players = players;
    }

    public int money() {
        return mainPot.money;
    }

    public int currentWager() {
        return mainPot.wager;
    }

    public void acceptBlindWagers(int smallBlindSize) {
        acceptBlindWager(players.smallBlind(), smallBlindSize);
        acceptBlindWager(players.bigBlind(), smallBlindSize * 2);
    }

    public void acceptCallFromPlayer(Player player) throws IsNotEnoughMoneyException {
        if (players.smallBlind().wager() == 0 || players.bigBlind().wager() == 0) {
            throw new RuntimeException("Can't accept call from player when blinds are not set");
        }
        int moneyTakingFromPlayer = mainPot.wager - player.wager();
        validateTakingMoneyFromPlayer(player, moneyTakingFromPlayer);
        mainPot.money += player.takeMoney(moneyTakingFromPlayer);
    }

    public void acceptRaiseFromPlayer(int additionalWager, Player player) throws
            IsNotEnoughMoneyException, NotPositiveWagerException {
        if (additionalWager <= 0) {
            throw new NotPositiveWagerException();
        }
        int moneyTakingFromPlayer = mainPot.wager + additionalWager - player.wager();
        validateTakingMoneyFromPlayer(player, moneyTakingFromPlayer);
        mainPot.money += player.takeMoney(moneyTakingFromPlayer);
        mainPot.wager += additionalWager;
    }

    public void acceptAllInFromPlayer(Player player) {
        mainPot.money += player.takeMoney(player.stack());
        if (player.wager() > mainPot.wager) {
            mainPot.wager = player.wager();
        }
    }

    public void giveMoneyToWinners(List<String> winners) {
        winners.forEach(winner -> players.get(winner).addMoney(mainPot.money / winners.size()));
        mainPot.money = 0;
    }

    public void giveMoneyToWinners(Player winner) {
        winner.addMoney(mainPot.money = 0);
    }

    private void acceptBlindWager(Player blind, int wager) {
        if (blind.stack() <= wager) {
            acceptAllInFromPlayer(blind);
        } else {
            mainPot.money += blind.takeMoney(wager);
            mainPot.wager = wager;
        }
    }

    private void validateTakingMoneyFromPlayer(Player player, int moneyTakingFromPlayer)
            throws IsNotEnoughMoneyException {
        if (player.stack() < moneyTakingFromPlayer) {
            throw new IsNotEnoughMoneyException("Try to make allIn");
        }
    }

    private static class Pot {
        private int money;
        private int wager;
    }

}
