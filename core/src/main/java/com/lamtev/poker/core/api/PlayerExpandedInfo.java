package com.lamtev.poker.core.api;

//TODO remove the class
@Deprecated
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
    }

    public void setWager(int wager) {
        this.wager = wager;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "PlayerExpandedInfo{" +
                "stack=" + stack +
                ", wager=" + wager +
                ", isActive=" + isActive +
                '}';
    }

}
