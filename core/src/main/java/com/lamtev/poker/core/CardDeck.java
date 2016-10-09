package com.lamtev.poker.core;

public class CardDeck {

    private Card[] cardDeck;

    public CardDeck() {
        cardDeck = new Card[52];
        initCards();
    }

    public Card cardAt(int index) {
        return cardDeck[index-1];
    }

    private void initCards() {
        byte index = 0;
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                cardDeck[index] = new Card(rank, suit);
                ++index;
            }
        }
    }

}
