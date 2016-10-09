package com.lamtev.poker.core;

import java.util.ArrayList;

public class CardDeck {

    private ArrayList<Card> cards;

    public CardDeck() {
        cards = new ArrayList<>(52);
        initCards();
    }

    public Card cardAt(int index) {
        return cards.get(index-1);
    }

    public Card giveTop() {
        return cards.remove(0);
    }

    private void initCards() {
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof CardDeck) {
            final CardDeck cardDeck = (CardDeck) obj;
            return cardDeck.cards == cards;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + cards.hashCode();
        hash = 29 * hash + cards.hashCode();
        return hash;
    }

}
