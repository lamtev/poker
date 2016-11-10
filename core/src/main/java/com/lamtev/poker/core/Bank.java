package com.lamtev.poker.core;

public class Bank {

    private int money = 0;
    private Players players;

    public Bank(Players players) {
        this.players = players;
    }

    public int getMoney() {
        return money;
    }

    public void takeMoneyFromPlayer(int money, int playerPosition) {
        this.money += players.get(playerPosition).takeMoney(money);
    }

    public void giveMoneyToPlayer(int money, int playerPosition) {
        players.get(playerPosition).addMoney(money);
        this.money -= money;
    }

}
