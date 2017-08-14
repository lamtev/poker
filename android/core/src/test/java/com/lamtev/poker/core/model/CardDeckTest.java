package com.lamtev.poker.core.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CardDeckTest extends CardsTest {

    @Test
    public void testCardDeck() {
        CardDeck cardDeck = new CardDeck();
        assertEquals(card3, cardDeck.cardAt(1));
        assertEquals(card4, cardDeck.cardAt(2));
        assertEquals(card1, cardDeck.cardAt(51));
        assertEquals(card2, cardDeck.cardAt(52));
        assertEquals(cardDeck, cardDeck);
    }

    @Test
    public void testInitCards() {
        CardDeck cardDeck = new CardDeck();
        assertEquals(52, cardDeck.size());
        cardDeck.pickUpTop();
        assertEquals(51, cardDeck.size());
        cardDeck.pickUpTop();
        assertEquals(50, cardDeck.size());
        cardDeck.initCards();
        assertEquals(52, cardDeck.size());
    }

    @Test
    public void testShuffle() {
        CardDeck cardDeck = new CardDeck();
        cardDeck.shuffle();
        assertNotEquals(new CardDeck(), cardDeck);
        cardDeck.shuffle();
        assertNotEquals(new CardDeck(), cardDeck);
    }

}
