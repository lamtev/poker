package com.lamtev.poker.core;

public final class Player {

    //TODO think about class for money and bank
    private int bank;
    private Cards cards;

    public Player(int bank) {
        this.bank = bank;
        cards = new Cards();
    }

    public int bank() {
        return bank;
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
        if (!cards.isEmpty()) {
            //TODO normal exception
            throw new RuntimeException();
        }
        while (!cards.isEmpty()) {
            cards.pickUpTop();
        }
    }

    public int giveMoney(int money) {
        bank -= money;
        return money;
    }

    public void takeMoney(int money) {
        bank += money;
    }

}
