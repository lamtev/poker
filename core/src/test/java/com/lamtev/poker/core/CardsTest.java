package com.lamtev.poker.core;

import org.junit.Test;

import static com.lamtev.poker.core.Rank.ACE;
import static com.lamtev.poker.core.Rank.TWO;
import static com.lamtev.poker.core.Suit.*;
import static org.junit.Assert.*;

public class CardsTest {

    static Card card1 = new Card(ACE, CLOVERS);
    static Card card2 = new Card(ACE, PIKES);
    static Card card3 = new Card(TWO, HEARTS);
    static Card card4 = new Card(TWO, TILES);

    @Test
    public void testCards() {
        Cards cards = new Cards();
        assertEquals(0, cards.size());
    }

    @Test
    public void testAdd() {
        Cards cards = new Cards();
        cards.add(card1);
        assertEquals(card1, cards.cardAt(1));

        cards.add(card2);
        assertEquals(card2, cards.cardAt(2));
    }

    @Test
    public void testPickUpTop() {
        CardDeck cardDeck = new CardDeck();
        assertEquals(card2, cardDeck.cardAt(cardDeck.size()));

        assertEquals(card2, cardDeck.pickUpTop());

        assertEquals(card1, cardDeck.cardAt(cardDeck.size()));
    }

}
