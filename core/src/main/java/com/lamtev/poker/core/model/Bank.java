package com.lamtev.poker.core.model;

import java.util.List;

public class Bank {

    //TODO think about ALL IN

    private static final int SMALL_BLIND_INDEX = 0;
    private static final int BIG_BLIND_INDEX = 1;

    private int money = 0;
    private Players players;
    private int currentWager = 0;
    private boolean blindsSet = false;

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

    public BlindsStatus acceptBlindWagers(int smallBlindSize) {
        BlindsStatus blindsStatus = BlindsStatus.USUAL_BLIND;

        Player smallBlind = players.get(SMALL_BLIND_INDEX);
        if (smallBlind.getStack() <= smallBlindSize) {
            acceptAllInFromPlayer(smallBlind);
            blindsStatus = BlindsStatus.ALL_IN;
            blindsStatus.setLatestAggressorIndex(SMALL_BLIND_INDEX);
        } else {
            money += smallBlind.takeMoney(smallBlindSize);
        }

        int bigBlindSize = 2 * smallBlindSize;
        Player bigBlind = players.get(BIG_BLIND_INDEX);
        if (bigBlind.getStack() <= bigBlindSize) {
            acceptAllInFromPlayer(bigBlind);
            blindsStatus = BlindsStatus.ALL_IN;
            blindsStatus.setLatestAggressorIndex(BIG_BLIND_INDEX);
        } else {
            money += players.get(BIG_BLIND_INDEX).takeMoney(bigBlindSize);
            currentWager = bigBlindSize;
        }
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
    }

    public void acceptRaiseFromPlayer(int additionalWager, Player player) throws Exception {
        int moneyTakingFromPlayer = currentWager + additionalWager - player.getWager();
        validateTakingMoneyFromPlayer(player, moneyTakingFromPlayer);
        this.money += player.takeMoney(moneyTakingFromPlayer);
        currentWager += additionalWager;
    }

    public void acceptAllInFromPlayer(Player player) {
        this.money += player.getStack();
        if (player.getWager() > currentWager) {
            currentWager = player.getWager();
        }
    }

    private void validateTakingMoneyFromPlayer(Player player, int moneyTakingFromPlayer) throws Exception {
        if (player.getStack() < moneyTakingFromPlayer) {
            throw new Exception("You have not got " + moneyTakingFromPlayer + ". Try to make allIn");
        }
    }

    //TODO think about remove this method
    public void giveMoneyToPlayer(int money, int playerPosition) {
        players.get(playerPosition).addMoney(money);
        this.money -= money;
    }

    public void giveMoneyToWinners(List<String> winners) {
        winners.forEach(winner -> players.get(winner).addMoney(money / winners.size()));
        money = 0;
    }

    public void giveMoneyToWinners(Player winner) {
        winner.addMoney(money = 0);
    }

}
