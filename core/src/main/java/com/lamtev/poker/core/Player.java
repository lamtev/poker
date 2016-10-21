package com.lamtev.poker.core;

public final class Player {

    //TODO think about class for money and stack
    private int stack;
    private Cards cards;

    public Player(int stack) {
        this.stack = stack;
        cards = new Cards();
    }

    public int stack() {
        return stack;
    }

    public Cards cards() {
        return cards;
    }

    public void takeCard(Card card) {
        if (cards.size() == 2) {
            //TODO normal exception
            throw new RuntimeException();
        }
        cards.add(card);
    }

    public void fold() {
        if (cards.isEmpty()) {
            //TODO normal exception
            throw new RuntimeException();
        }
        while (!cards.isEmpty()) {
            cards.pickUpTop();
        }
    }

    public int giveMoney(int money) {
        stack -= money;
        return money;
    }

    public void takeMoney(int money) {
        stack += money;
    }

}
