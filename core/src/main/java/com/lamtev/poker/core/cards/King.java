package com.lamtev.poker.core.cards;

public final class King implements Card {

    private final Suit suit;

    public King(Suit suit) {
        this.suit = suit;
    }

    public Suit suit() {
        return suit;
    }
}
