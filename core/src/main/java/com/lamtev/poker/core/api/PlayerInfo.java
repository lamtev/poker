package com.lamtev.poker.core.api;

public class PlayerInfo {

    private final String id;
    private final int stack;

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
