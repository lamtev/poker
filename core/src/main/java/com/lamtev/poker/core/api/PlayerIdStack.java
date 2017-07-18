package com.lamtev.poker.core.api;

public class PlayerIdStack {

    private final String id;
    private final int stack;

    public PlayerIdStack(String id, int stack) {
        this.id = id;
        this.stack = stack;
    }

    public String id() {
        return id;
    }

    public int stack() {
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
