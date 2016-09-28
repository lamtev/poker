package com.lamtev.poker.core.cards;

public class Jack implements Card {

    private final Suit suit;

    public Jack(Suit suit) {
        this.suit = suit;
    }

    public Suit suit() {
        return suit;
    }
}
