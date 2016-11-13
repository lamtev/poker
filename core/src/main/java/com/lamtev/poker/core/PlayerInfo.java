package com.lamtev.poker.core;

public class PlayerInfo {

    private String id;
    private int stack;

    public PlayerInfo(String id, int stack) {
        this.id = id;
        this.stack = stack;
    }

    public String getId() {
        return id;
    }

    public int getStack() {
        return stack;
    }

}
