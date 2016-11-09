package com.lamtev.poker.core;

public final class Player {

    private int stack;
    private int wager = 0;
    private Cards cards;
    private boolean isActive = true;
    //TODO think about ID field

    public Player(int stack) {
        this.stack = stack;
        this.cards = new Cards();
    }

    public int getStack() {
        return stack;
    }

    public int getWager() {
        return wager;
    }

    public Cards getCards() {
        return cards;
    }

    public boolean isActive() {
        return isActive;
    }

    public void addCard(Card card) {
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
        isActive = false;
    }

    public void addMoney(int money) {
        stack += money;
    }

    public int takeMoney(int money) {
        stack -= money;
        wager += money;
        return money;
    }

}
