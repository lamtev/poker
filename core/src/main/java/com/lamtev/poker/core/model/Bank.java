package com.lamtev.poker.core.model;

import java.util.ArrayList;
import java.util.List;

public final class Bank {

    //TODO think about ALL IN

    private int money = 0;
    private Players players;
    private int currentWager = 0;
    private boolean blindsSet = false;
    private BlindsStatus blindsStatus = BlindsStatus.USUAL_BLIND;
    private List<Player> allInners = new ArrayList<>();

    //TODO
    public enum BlindsStatus {
        USUAL_BLIND,
        ALL_IN;

        private int latestAggressorIndex;

        public void setLatestAggressorIndex(int latestAggressorIndex) {
            this.latestAggressorIndex = latestAggressorIndex;
        }

        public int getLatestAggressorIndex() {
            return latestAggressorIndex;
        }

    }

    public Bank(Players players) {
        this.players = players;
    }

    public int getMoney() {
        return money;
    }

    public int getCurrentWager() {
        return currentWager;
    }

    public List<Player> getAllInners() {
        return allInners;
    }

    public BlindsStatus acceptBlindWagers(Player smallBlind, Player bigBlind, int smallBlindSize) {

        acceptBlindWager(smallBlind, smallBlindSize);
        acceptBlindWager(bigBlind, smallBlindSize * 2);
        blindsSet = true;
        return blindsStatus;
    }

    public void acceptCallFromPlayer(Player player) throws Exception {
        if (!blindsSet) {
            throw new RuntimeException("Can't accept call from player when blinds not set");
        }
        int moneyTakingFromPlayer = currentWager - player.getWager();
        validateTakingMoneyFromPlayer(player, moneyTakingFromPlayer);
        this.money += player.takeMoney(moneyTakingFromPlayer);
        if (thisBetIsAllIn(player)) {
            allInners.add(player);
        }
    }

    public void acceptRaiseFromPlayer(int additionalWager, Player player) throws Exception {
        int moneyTakingFromPlayer = currentWager + additionalWager - player.getWager();
        validateTakingMoneyFromPlayer(player, moneyTakingFromPlayer);
        this.money += player.takeMoney(moneyTakingFromPlayer);
        if (thisBetIsAllIn(player)) {
            allInners.add(player);
        }
        currentWager += additionalWager;
    }

    public void acceptAllInFromPlayer(Player player) {
        this.money += player.takeMoney(player.getStack());
        allInners.add(player);
        if (player.getWager() > currentWager) {
            currentWager = player.getWager();
        }
    }

    public void giveMoneyToWinners(List<String> winners) {
        winners.forEach(winner -> players.get(winner).addMoney(money / winners.size()));
        money = 0;
    }

    public void giveMoneyToWinners(Player winner) {
        winner.addMoney(money = 0);
    }

    private boolean thisBetIsAllIn(Player player) {
        return player.getStack() == 0;
    }

    private void acceptBlindWager(Player blind, int wager) {
        if (blind.getStack() <= wager) {
            acceptAllInFromPlayer(blind);
            blindsStatus = BlindsStatus.ALL_IN;
            blindsStatus.setLatestAggressorIndex(players.indexOf(blind));
        } else {
            money += blind.takeMoney(wager);
            currentWager = wager;
        }
    }

    private void validateTakingMoneyFromPlayer(Player player, int moneyTakingFromPlayer) throws Exception {
        if (player.getStack() < moneyTakingFromPlayer) {
            throw new Exception("You have not got " + moneyTakingFromPlayer + ". Try to make allIn");
        }
    }

}
