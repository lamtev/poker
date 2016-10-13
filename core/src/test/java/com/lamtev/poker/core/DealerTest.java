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
    public void testPreflop() {
        Dealer dealer = new Dealer(players);
        dealer.preflop();
        for (Player player : dealer.players()) {
            assertEquals(2, player.cards().size());
        }
    }

    @Test(expected = RuntimeException.class)
    public void testFlop() {
        Dealer dealer = new Dealer(players);
        dealer.flop();
        assertEquals(3, dealer.commonCards().size());

        dealer.flop();
    }

    @Test(expected = RuntimeException.class)
    public void testTurn() {
        Dealer dealer = new Dealer(players);
        dealer.turn();

        dealer.flop();
        dealer.turn();
        assertEquals(4, dealer.commonCards().size());

        dealer.turn();
    }

    @Test(expected = RuntimeException.class)
    public void testRiver() {
        Dealer dealer = new Dealer(players);
        dealer.river();

        dealer.flop();
        dealer.river();

        dealer.turn();
        dealer.river();
        assertEquals(5, dealer.commonCards().size());

        dealer.river();
    }

}
