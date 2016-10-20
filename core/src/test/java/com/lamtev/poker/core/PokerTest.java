package com.lamtev.poker.core;

import org.junit.Test;
import static org.junit.Assert.*;

public class PokerTest {

    @Test(expected = IndexOutOfBoundsException.class)
    public void testCall() {
        Poker poker = new Poker(5, 10, 500);
        poker.call();
        assertEquals(3, poker.getMoves());
    }

}
