package com.lamtev.poker.core.api;

import com.lamtev.poker.core.model.Card;

import java.util.List;

public class User implements Player {

    private final String id;
    private int stack;
    private int wager;
    private List<Card> cards;
    private boolean isActive;

    public User(String id, int stack) {
        this.id = id;
        this.stack = stack;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public int stack() {
        return stack;
    }

    @Override
    public int wager() {
        return wager;
    }

    @Override
    public List<Card> cards() {
        return cards;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", stack=" + stack +
                ", wager=" + wager +
                ", cards=" + cards +
                ", isActive=" + isActive +
                '}';
    }

}
