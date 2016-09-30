package com.lamtev.poker.core.cards;

public class TexasHoldemCardDeck implements CardDeck {

    private final int size = 52;
    private Card[] cardDeck;

    public TexasHoldemCardDeck() {
        cardDeck = new Card[size];
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
