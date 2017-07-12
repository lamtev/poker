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
    private BlindsStatus blindsStatus = BlindsStatus.USUAL_BLIND;
    private List<Player> allInners = new ArrayList<>();
    public Bank(Players players) {
        this.players = players;
    }

    public int getMoney() {
        return mainPot.money;
    }

    public int getCurrentWager() {
        return mainPot.wager;
    }

    public List<Player> getAllInners() {
        return allInners;
    }

    public BlindsStatus acceptBlindWagers(int smallBlindSize) {
        acceptBlindWager(players.smallBlind(), smallBlindSize);
        acceptBlindWager(players.bigBlind(), smallBlindSize * 2);
        blindsSet = true;
        return blindsStatus;
    }

    public void acceptCallFromPlayer(Player player) throws IsNotEnoughMoneyException {
        if (!blindsSet) {
            throw new RuntimeException("Can't accept call from player when blinds are not set");
        }
        int moneyTakingFromPlayer = mainPot.wager - player.getWager();
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
        int moneyTakingFromPlayer = mainPot.wager + additionalWager - player.getWager();
        validateTakingMoneyFromPlayer(player, moneyTakingFromPlayer);
        mainPot.money += player.takeMoney(moneyTakingFromPlayer);
        mainPot.wager += additionalWager;
        mainPot.applicants.add(player);
        if (thisBetIsAllIn(player)) {
            allInners.add(player);
        }
    }

    public void acceptAllInFromPlayer(Player player) {
        mainPot.money += player.takeMoney(player.getStack());
        mainPot.applicants.add(player);
        allInners.add(player);
        if (player.getWager() > mainPot.wager) {
            mainPot.wager = player.getWager();
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
        return player.getStack() == 0;
    }

    private void acceptBlindWager(Player blind, int wager) {
        if (blind.getStack() <= wager) {
            acceptAllInFromPlayer(blind);
            blindsStatus = BlindsStatus.ALL_IN;
            blindsStatus.setLatestAggressor(blind);
        } else {
            mainPot.money += blind.takeMoney(wager);
            mainPot.wager = wager;
        }
    }

    private void validateTakingMoneyFromPlayer(Player player, int moneyTakingFromPlayer) throws IsNotEnoughMoneyException {
        if (player.getStack() < moneyTakingFromPlayer) {
            throw new IsNotEnoughMoneyException("Try to make allIn");
        }
    }

    //TODO
    public enum BlindsStatus {
        USUAL_BLIND,
        ALL_IN;

        private Player latestAggressor;

        public Player getLatestAggressor() {
            return latestAggressor;
        }

        public void setLatestAggressor(Player latestAggressor) {
            this.latestAggressor = latestAggressor;
        }

    }

    private static class Pot {
        private int money;
        private int wager;
        private Set<Player> applicants = new HashSet<>();
    }

}
