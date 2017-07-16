package com.lamtev.poker.core.api;

public class PlayerIdStack {

    private final String id;
    private final int stack;

    public PlayerIdStack(String id, int stack) {
        this.id = id;
        this.stack = stack;
    }

    public String getId() {
        return id;
    }

    public int getStack() {
        return stack;
    }

    @Override
    public String toString() {
        return "PlayerIdStack{" +
                "id='" + id + '\'' +
                ", stack=" + stack +
                '}';
    }
}
