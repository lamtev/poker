package com.lamtev.poker.core;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class DealerTest {

    private static ArrayList<Player> players;

    static {
        players = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            players.add(new Player(100));
        }
    }

    @Test
    public void testMakePreflop() {
        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        for (Player player : players) {
            assertEquals(2, player.cards().size());
        }
    }

    @Test(expected = RuntimeException.class)
    public void testMakeFlop() {
        Dealer dealer = new Dealer(players);
        dealer.makeFlop();
        assertEquals(3, dealer.commonCards().size());

        dealer.makeFlop();
    }

    @Test(expected = RuntimeException.class)
    public void testMakeTurn() {
        Dealer dealer = new Dealer(players);
        dealer.makeTurn();

        dealer.makeFlop();
        dealer.makeTurn();
        assertEquals(4, dealer.commonCards().size());

        dealer.makeTurn();
    }

    @Test(expected = RuntimeException.class)
    public void testMakeRiver() {
        Dealer dealer = new Dealer(players);
        dealer.makeRiver();

        dealer.makeFlop();
        dealer.makeRiver();

        dealer.makeTurn();
        dealer.makeRiver();
        assertEquals(5, dealer.commonCards().size());

        dealer.makeRiver();
    }

}
