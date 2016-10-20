package com.lamtev.poker.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

public class CardDeck extends Cards {

    public CardDeck() {
        cards = new ArrayList<>(52);
        initCards();
    }

    public void initCards() {
        cards.clear();
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards, new Random(new Date().getTime()));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Cards) {
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
