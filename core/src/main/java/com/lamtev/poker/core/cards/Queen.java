package com.lamtev.poker.core.cards;

final class Queen implements Card {

    private final Suit suit;

    public Queen(Suit suit) {
        this.suit = suit;
    }

    public Suit suit() {
        return suit;
    }
}
