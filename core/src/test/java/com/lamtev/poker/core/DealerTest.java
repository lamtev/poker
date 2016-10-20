package com.lamtev.poker.core;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DealerTest {

    @Test
    public void testMakePreflop() {
        List<Player> players = new ArrayList<Player>() {{
            for (int i = 0; i < 5; ++i) {
                add(new Player(100));
            }
        }};

        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        for (Player player : players) {
            assertEquals(2, player.cards().size());
        }
    }

    @Test(expected = RuntimeException.class)
    public void testMakePreflopExceptionThrowing() {
        List<Player> players = new ArrayList<Player>() {{
            for (int i = 0; i < 5; ++i) {
                add(new Player(100));
            }
        }};

        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        dealer.makePreflop();
    }

    @Test
    public void testMakeFlop() {
        List<Player> players = new ArrayList<Player>() {{
            for (int i = 0; i < 5; ++i) {
                add(new Player(100));
            }
        }};

        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        dealer.makeFlop();
        assertEquals(3, dealer.commonCards().size());
    }

    @Test(expected = RuntimeException.class)
    public void testMakeFlopExceptionThrowing1() {
        List<Player> players = new ArrayList<Player>() {{
            for (int i = 0; i < 5; ++i) {
                add(new Player(100));
            }
        }};

        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        dealer.makeFlop();
        dealer.makeFlop();
    }

    @Test(expected = RuntimeException.class)
    public void testMakeFlopExceptionThrowing2() {
        List<Player> players = new ArrayList<Player>() {{
            for (int i = 0; i < 5; ++i) {
                add(new Player(100));
            }
        }};

        Dealer dealer = new Dealer(players);
        dealer.makeFlop();
    }

    @Test
    public void testMakeTurn() {
        List<Player> players = new ArrayList<Player>() {{
            for (int i = 0; i < 5; ++i) {
                add(new Player(100));
            }
        }};

        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        dealer.makeFlop();
        dealer.makeTurn();
        assertEquals(4, dealer.commonCards().size());
    }

    @Test(expected = RuntimeException.class)
    public void testMakeTurnExceptionThrowing1() {
        List<Player> players = new ArrayList<Player>() {{
            for (int i = 0; i < 5; ++i) {
                add(new Player(100));
            }
        }};

        Dealer dealer = new Dealer(players);
        dealer.makeTurn();
    }

    @Test(expected = RuntimeException.class)
    public void testMakeTurnExceptionThrowing2() {
        List<Player> players = new ArrayList<Player>() {{
            for (int i = 0; i < 5; ++i) {
                add(new Player(100));
            }
        }};

        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        dealer.makeTurn();
    }

    @Test(expected = RuntimeException.class)
    public void testMakeTurnExceptionThrowing3() {
        List<Player> players = new ArrayList<Player>() {{
            for (int i = 0; i < 5; ++i) {
                add(new Player(100));
            }
        }};

        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        dealer.makeFlop();
        dealer.makeTurn();
        dealer.makeTurn();
    }



    @Test
    public void testMakeRiver() {
        List<Player> players = new ArrayList<Player>() {{
            for (int i = 0; i < 5; ++i) {
                add(new Player(100));
            }
        }};
        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        dealer.makeFlop();
        dealer.makeTurn();
        dealer.makeRiver();

        assertEquals(5, dealer.commonCards().size());
    }

    @Test(expected = RuntimeException.class)
    public void testMakeRiverExceptionThrowing1() {
        List<Player> players = new ArrayList<Player>() {{
            for (int i = 0; i < 5; ++i) {
                add(new Player(100));
            }
        }};

        Dealer dealer = new Dealer(players);
        dealer.makeRiver();
    }

    @Test(expected = RuntimeException.class)
    public void testMakeRiverExceptionThrowing2() {
        List<Player> players = new ArrayList<Player>() {{
            for (int i = 0; i < 5; ++i) {
                add(new Player(100));
            }
        }};

        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        dealer.makeRiver();
    }

    @Test(expected = RuntimeException.class)
    public void testMakeRiverExceptionThrowing3() {
        List<Player> players = new ArrayList<Player>() {{
            for (int i = 0; i < 5; ++i) {
                add(new Player(100));
            }
        }};

        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        dealer.makeFlop();
        dealer.makeRiver();
    }

    @Test(expected = RuntimeException.class)
    public void testMakeRiverExceptionThrowing4() {
        List<Player> players = new ArrayList<Player>() {{
            for (int i = 0; i < 5; ++i) {
                add(new Player(100));
            }
        }};

        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        dealer.makeFlop();
        dealer.makeTurn();
        dealer.makeRiver();
        dealer.makeRiver();
    }

}
