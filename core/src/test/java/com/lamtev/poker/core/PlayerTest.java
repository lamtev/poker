package com.lamtev.poker.core;

import org.junit.Test;

import static org.junit.Assert.*;
import static com.lamtev.poker.core.Rank.*;
import static com.lamtev.poker.core.Suit.*;

public class PlayerTest {

    @Test(expected = RuntimeException.class)
    public void testTakeCard() {
        Player player = new Player(100);
        Card card1 = new Card(ACE, PIKES);
        player.takeCard(card1);
        assertEquals(card1, player.cards().cardAt(1));

        Card card2 = new Card(FIVE, HEARTS);
        player.takeCard(card2);
        assertEquals(card2, player.cards().cardAt(2));

        player.takeCard(new Card(ACE, TILES));
    }

    @Test
    public void testGiveMoney() {
        Player player = new Player(1000);
        player.giveMoney(900);
        assertEquals(100, player.bank());
    }

    @Test
    public void testTakeMoney() {
        Player player = new Player(1000);
        player.takeMoney(500);
        assertEquals(1500, player.bank());
    }

    @Test(expected = RuntimeException.class)
    public void testFold() {
        Player player = new Player(1000);
        player.fold();
        player.takeCard(new Card(ACE, TILES));
        assertEquals(1, player.cards().size());

        player.takeCard(new Card(FIVE, TILES));
        assertEquals(2, player.cards().size());

        player.fold();
        assertEquals(0, player.cards().size());
    }

}
