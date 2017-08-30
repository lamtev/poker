package com.lamtev.poker.core.model;

import java.util.Collections;

final class CardDeck extends Cards {

    CardDeck() {
        super();
        initCards();
    }

    void shuffle() {
        Collections.shuffle(cards);
    }

    private void initCards() {
        cards.clear();
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    @Override
    public void add(Card card) {
        throw new UnsupportedOperationException();
    }

}
