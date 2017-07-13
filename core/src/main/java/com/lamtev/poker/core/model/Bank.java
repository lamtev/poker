package com.lamtev.poker.core.model;

import com.lamtev.poker.core.states.exceptions.IsNotEnoughMoneyException;
import com.lamtev.poker.core.states.exceptions.NotPositiveWagerException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Bank {

    //TODO side pots
    private Pot mainPot = new Pot();
    private Players players;
    private boolean blindsSet = false;
    private List<Player> allInners = new ArrayList<>();

    public Bank(Players players) {
        this.players = players;
    }

    public int money() {
        return mainPot.money;
    }

    public int currentWager() {
        return mainPot.wager;
    }

    public List<Player> allInners() {
        return allInners;
    }

    public void acceptBlindWagers(int smallBlindSize) {
        acceptBlindWager(players.smallBlind(), smallBlindSize);
        acceptBlindWager(players.bigBlind(), smallBlindSize * 2);
        blindsSet = true;
    }

    public void acceptCallFromPlayer(Player player) throws IsNotEnoughMoneyException {
        if (!blindsSet) {
            throw new RuntimeException("Can't accept call from player when blinds are not set");
        }
        int moneyTakingFromPlayer = mainPot.wager - player.wager();
        validateTakingMoneyFromPlayer(player, moneyTakingFromPlayer);
        mainPot.money += player.takeMoney(moneyTakingFromPlayer);
        mainPot.applicants.add(player);
        if (thisBetIsAllIn(player)) {
            allInners.add(player);
        }
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
        mainPot.applicants.add(player);
        if (thisBetIsAllIn(player)) {
            allInners.add(player);
        }
    }

    public void acceptAllInFromPlayer(Player player) {
        mainPot.money += player.takeMoney(player.stack());
        mainPot.applicants.add(player);
        allInners.add(player);
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

    private boolean thisBetIsAllIn(Player player) {
        return player.stack() == 0;
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
        private Set<Player> applicants = new HashSet<>();
    }

}
