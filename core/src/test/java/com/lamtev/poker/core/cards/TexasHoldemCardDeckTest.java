package com.lamtev.poker.core.cards;

import org.junit.Test;
import static org.junit.Assert.*;
import static com.lamtev.poker.core.cards.Rank.*;
import static com.lamtev.poker.core.cards.Suit.*;

public class TexasHoldemCardDeckTest {

    @Test
    public void testTexasHoldemCardDeck() {
        CardDeck cardDeck = new TexasHoldemCardDeck();
        assertEquals(new Card(TWO, HEARTS), cardDeck.cardAt(1));
        assertEquals(new Card(TWO, TILES), cardDeck.cardAt(2));
        assertEquals(new Card(ACE, CLOVERS), cardDeck.cardAt(51));
        assertEquals(new Card(ACE, PIKES), cardDeck.cardAt(52));
    }

}
