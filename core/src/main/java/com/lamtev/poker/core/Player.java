package com.lamtev.poker.core;

import java.util.ArrayList;

public class Player {

    private int bank;
    private ArrayList<Card> cards;

    public Player(int bank) {
        this.bank = bank;
        cards = new ArrayList<>();
    }

    public int bank() {
        return bank;
    }


    public ArrayList<Card> cards() {
        return new ArrayList<>(cards);
    }

    public void takeCard(Card card) {
        if (cards.size() == 2) {
            //TODO normal exception
            throw new RuntimeException();
        }
        cards.add(card);
    }

    public void fold() {
        if (cards.size() == 0) {
            //TODO normal exception
            throw new RuntimeException();
        }
        cards.clear();
    }

    public int giveMoney(int money) {
        bank -= money;
        return money;
    }

    public void takeMoney(int money) {
        bank += money;
    }

}
