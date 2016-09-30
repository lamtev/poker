package com.lamtev.poker.core.cards;

import java.util.Objects;

public final class Card {

    private Rank rank;
    private Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank rank() {
        return rank;
    }

    public Suit suit() {
        return suit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Card) {
            final Card card = (Card) o;
            return card.rank == rank && card.suit == suit;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + rank.hashCode();
        hash = 29 * hash + suit.hashCode();
        return hash;
    }

}
