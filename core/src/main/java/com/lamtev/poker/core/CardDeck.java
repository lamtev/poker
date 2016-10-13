package com.lamtev.poker.core;

import java.util.ArrayList;
import java.util.Random;

public class CardDeck {

    ArrayList<Card> cards;

    public CardDeck() {
        cards = new ArrayList<>(52);
        initCards();
    }

    public Card giveTop() {
        return cards.remove(0);
    }

    public void shuffle() {
        Random rnd = new Random(13);
        for (int i = 0; i < 79; ++i) {
            int i1 = Math.abs(rnd.nextInt()*i - i*rnd.hashCode()) % 52;
            int i2 = Math.abs(rnd.nextInt()*13*i - i*i + 1234567) % 52;
            swap(cards, i1, i2);
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

    Card cardAt(int index) {
        return cards.get(index-1);
    }

    private void initCards() {
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    private void swap(ArrayList<Card> cards, int index1, int index2) {
        Card card = cards.get(index1);
        cards.set(index1, cards.get(index2));
        cards.set(index2, card);
    }

}
