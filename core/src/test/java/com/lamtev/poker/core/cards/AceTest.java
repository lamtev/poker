package com.lamtev.poker.core.cards;

import org.junit.*;
import static org.junit.Assert.*;
import static com.lamtev.poker.core.cards.Suit.*;

public class AceTest {

    @Test
    public void testAce() {
        Card ace = new Ace(PIKES);
        assertEquals(PIKES, ace.suit());
    }

}
