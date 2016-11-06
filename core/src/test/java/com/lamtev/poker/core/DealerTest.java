package com.lamtev.poker.core;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DealerTest {

    List<Player> players = new ArrayList<Player>() {{
        for (int i = 0; i < 5; ++i) {
            add(new Player(100));
        }
    }};

    @Test
    public void testMakePreflop() {
        //TODO этих игроков можно сделать полем в классе? одинаковые ведь из раза в раз
        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        for (Player player : players) {
            assertEquals(2, player.cards().size());
        }
    }

    @Test(expected = RuntimeException.class)
    public void testMakePreflopExceptionThrowing() {
        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        dealer.makePreflop();
    }

    @Test
    public void testMakeFlop() {
        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        dealer.makeFlop();
        assertEquals(3, dealer.commonCards().size());
    }

    @Test(expected = RuntimeException.class)
    public void testMakeFlopExceptionThrowing1() {
        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        dealer.makeFlop();
        dealer.makeFlop();
    }

    @Test(expected = RuntimeException.class)
    public void testMakeFlopExceptionThrowing2() {
        Dealer dealer = new Dealer(players);
        dealer.makeFlop();
    }

    @Test
    public void testMakeTurn() {
        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        dealer.makeFlop();
        dealer.makeTurn();
        assertEquals(4, dealer.commonCards().size());
    }

    @Test(expected = RuntimeException.class)
    public void testMakeTurnExceptionThrowing1() {
        Dealer dealer = new Dealer(players);
        dealer.makeTurn();
    }

    @Test(expected = RuntimeException.class)
    public void testMakeTurnExceptionThrowing2() {
        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        dealer.makeTurn();
    }

    @Test(expected = RuntimeException.class)
    public void testMakeTurnExceptionThrowing3() {
        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        dealer.makeFlop();
        dealer.makeTurn();
        dealer.makeTurn();
    }



    @Test
    public void testMakeRiver() {
        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        dealer.makeFlop();
        dealer.makeTurn();
        dealer.makeRiver();

        assertEquals(5, dealer.commonCards().size());
    }

    @Test(expected = RuntimeException.class)
    public void testMakeRiverExceptionThrowing1() {
        Dealer dealer = new Dealer(players);
        dealer.makeRiver();
    }

    @Test(expected = RuntimeException.class)
    public void testMakeRiverExceptionThrowing2() {
        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        dealer.makeRiver();
    }

    @Test(expected = RuntimeException.class)
    public void testMakeRiverExceptionThrowing3() {
        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        dealer.makeFlop();
        dealer.makeRiver();
    }

    @Test(expected = RuntimeException.class)
    public void testMakeRiverExceptionThrowing4() {
        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        dealer.makeFlop();
        dealer.makeTurn();
        dealer.makeRiver();
        dealer.makeRiver();
    }

}
