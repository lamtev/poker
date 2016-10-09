package com.lamtev.poker.core;

import org.junit.Test;
import static org.junit.Assert.*;
import static com.lamtev.poker.core.Rank.*;
import static com.lamtev.poker.core.Suit.*;

public class CardDeckTest {

    private Card card1 = new Card(TWO, HEARTS);
    private Card card2 = new Card(TWO, TILES);
    private Card card3 = new Card(ACE, CLOVERS);
    private Card card4 = new Card(ACE, PIKES);

    @Test
    public void testCardDeck() {
        CardDeck cardDeck = new CardDeck();
        assertEquals(card1, cardDeck.cardAt(1));
        assertEquals(card2, cardDeck.cardAt(2));
        assertEquals(card3, cardDeck.cardAt(51));
        assertEquals(card4, cardDeck.cardAt(52));

        assertEquals(cardDeck, cardDeck);

        //BUG
        assertEquals(new CardDeck(), cardDeck);
    }

    @Test
    public void testGiveTop() {
        CardDeck cardDeck = new CardDeck();
        assertEquals(card1, cardDeck.cardAt(1));

        assertEquals(card1, cardDeck.giveTop());

        assertEquals(card2, cardDeck.cardAt(1));

    }

}
