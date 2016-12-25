package com.lamtev.poker.core.api;

public class PlayerMoney {

    private final int stack;
    private final int wager;

    public PlayerMoney(int stack, int wager) {
        this.stack = stack;
        this.wager = wager;
    }

    public int getStack() {
        return stack;
    }

    public int getWager() {
        return wager;
    }

    @Override
    public String toString() {
        return wager + " " + stack;
    }
}
