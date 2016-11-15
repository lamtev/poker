package com.lamtev.poker.core.model;

public class Bank {

    //TODO think about ALL IN

    private static final int SMALL_BLIND_INDEX = 0;
    private static final int BIG_BLIND_INDEX = 1;

    private int money = 0;
    private Players players;
    private int currentWager = 0;

    public Bank(Players players) {
        this.players = players;
    }

    public int getMoney() {
        return money;
    }

    public int getCurrentWager() {
        return currentWager;
    }

    public void acceptBlindWagers(int smallBlindSize) {
        money += players.get(SMALL_BLIND_INDEX).takeMoney(smallBlindSize);
        int bigBlindSize = 2 * smallBlindSize;
        money += players.get(BIG_BLIND_INDEX).takeMoney(bigBlindSize);
        currentWager = bigBlindSize;
    }

    public void acceptCallFromPlayer(int playerPosition) throws Exception {
        //TODO throw exception when blinds not set
        Player player = players.get(playerPosition);
        int playerWager = player.getWager();
        int moneyTakingFromPlayer = currentWager - playerWager;
        validateTakingMoneyFromPlayer(player, moneyTakingFromPlayer);
        this.money += player.takeMoney(moneyTakingFromPlayer);
    }

    public void acceptRaiseFromPlayer(int additionalWager, int playerIndex) throws Exception {
        Player player = players.get(playerIndex);
        int playerWager = player.getWager();
        int moneyTakingFromPlayer = currentWager + additionalWager - playerWager;
        validateTakingMoneyFromPlayer(player, moneyTakingFromPlayer);
        this.money += player.takeMoney(moneyTakingFromPlayer);
        currentWager += additionalWager;
    }

    private void validateTakingMoneyFromPlayer(Player player, int moneyTakingFromPlayer) throws Exception {
        if (player.getStack() < moneyTakingFromPlayer) {
            throw new Exception();
        }
    }

    public void giveMoneyToPlayer(int money, int playerPosition) {
        players.get(playerPosition).addMoney(money);
        this.money -= money;
    }

}
