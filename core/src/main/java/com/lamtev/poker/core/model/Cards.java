package com.lamtev.poker.core.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class Cards implements Iterable<Card> {

    List<Card> cards = new ArrayList<>();

    public Card cardAt(int index) {
        return cards.get(index - 1);
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
        return cards.remove(size() - 1);
    }

    @Override
    public void forEach(Consumer<? super Card> action) {
        cards.forEach(action);
    }

    @Override
    public Iterator<Card> iterator() {
        return cards.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cards cards1 = (Cards) o;

        return cards != null ? cards.equals(cards1.cards) : cards1.cards == null;
    }

    @Override
    public int hashCode() {
        return cards != null ? cards.hashCode() : 0;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Card card : cards) {
            stringBuilder
                    .append(card.toString())
                    .append(" ");
        }

        return stringBuilder.toString();
    }
}
