package com.lamtev.poker.core;

import org.junit.Test;
import static org.junit.Assert.*;
import static com.lamtev.poker.core.Rank.*;
import static com.lamtev.poker.core.Suit.*;

public class CardDeckTest {

    @Test
    public void testCardDeck() {
        CardDeck cardDeck = new CardDeck();
        assertEquals(new Card(TWO, HEARTS), cardDeck.cardAt(1));
        assertEquals(new Card(TWO, TILES), cardDeck.cardAt(2));
        assertEquals(new Card(ACE, CLOVERS), cardDeck.cardAt(51));
        assertEquals(new Card(ACE, PIKES), cardDeck.cardAt(52));
    }

}
