package com.lamtev.poker.core.api;

public class PlayerExpandedInfo {

    private int stack;
    private int wager;
    private boolean isActive;

    public PlayerExpandedInfo(int stack, int wager, boolean isActive) {
        this.stack = stack;
        this.wager = wager;
        this.isActive = isActive;
    }

    public int getStack() {
        return stack;
    }

    public int getWager() {
        return wager;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setStack(int stack) {
        this.stack = stack;
    }   //TODO зачем делать поля приватными, а потом создавать set-методы на них?
                                                              //TODO можно выделить общую часть с классом PlayerMoney
    public void setWager(int wager) {
        this.wager = wager;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
