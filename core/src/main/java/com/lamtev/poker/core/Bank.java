package com.lamtev.poker.core;

import java.util.List;

public class Bank {

    private int money = 0;
    private List<Player> players;

    public Bank(List<Player> players) {
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
