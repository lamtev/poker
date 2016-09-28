package com.lamtev.poker.core.cards;

public final class Ace implements Card {

    private final Suit suit;

    public Ace(Suit suit) {
        this.suit = suit;
    }

    public Suit suit() {
        return suit;
    }

}
