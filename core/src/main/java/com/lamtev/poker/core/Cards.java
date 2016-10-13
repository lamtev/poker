package com.lamtev.poker.core;

import java.util.ArrayList;

public class Cards {

    protected ArrayList<Card> cards;

    public Cards() {
        cards = new ArrayList<>();
    }

    public Card cardAt(int index) {
        return cards.get(index-1);
    }

    public int size() {
        return cards.size();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public void add(Card card) {
        cards.add(card);
    }

    public Card pickUpTop() {
        return cards.remove(size()-1);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Cards) {
            final Cards cards = (Cards) obj;
            return this.cards.equals(cards.cards);
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
