package com.lamtev.poker.core;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DealerTest {

    private List<Player> generatePlayers() {
        return new ArrayList<Player>() {{
            for (int i = 0; i < 5; ++i) {
                add(new Player(100));
            }
        }};
    }

    private Dealer generateDealer() {
        return new Dealer(
                generatePlayers(),
                new Cards()
        );
    }

    @Test
    public void testMakePreflop() throws Exception {
        List<Player> players = generatePlayers();
        Dealer dealer = new Dealer(players, new Cards());
        dealer.makePreflop();
        for (Player player : players) {
            assertEquals(2, player.getCards().size());
        }
    }

    @Test(expected = Exception.class)
    public void testMakePreflopExceptionThrowing() throws Exception {
        Dealer dealer = generateDealer();
        dealer.makePreflop();
        dealer.makePreflop();
    }

    @Test
    public void testMakeFlop() throws Exception {
        List<Player> players = generatePlayers();
        Cards commonCards = new Cards();
        Dealer dealer = new Dealer(players, commonCards);
        dealer.makePreflop();
        dealer.makeFlop();
        assertEquals(3, commonCards.size());
    }

    @Test(expected = Exception.class)
    public void testMakeFlopExceptionThrowing1() throws Exception {
        Dealer dealer = generateDealer();
        dealer.makePreflop();
        dealer.makeFlop();
        dealer.makeFlop();
    }

    @Test(expected = Exception.class)
    public void testMakeFlopExceptionThrowing2() throws Exception {
        Dealer dealer = generateDealer();
        dealer.makeFlop();
    }

    @Test
    public void testMakeTurn() throws Exception {
        List<Player> players = generatePlayers();
        Cards commonCards = new Cards();
        Dealer dealer = new Dealer(players, commonCards);
        dealer.makePreflop();
        dealer.makeFlop();
        dealer.makeTurn();
        assertEquals(4, commonCards.size());
    }

    @Test(expected = Exception.class)
    public void testMakeTurnExceptionThrowing1() throws Exception {
        Dealer dealer = generateDealer();
        dealer.makeTurn();
    }

    @Test(expected = Exception.class)
    public void testMakeTurnExceptionThrowing2() throws Exception {
        Dealer dealer = generateDealer();
        dealer.makePreflop();
        dealer.makeTurn();
    }

    @Test(expected = Exception.class)
    public void testMakeTurnExceptionThrowing3() throws Exception {
        Dealer dealer = generateDealer();
        dealer.makePreflop();
        dealer.makeFlop();
        dealer.makeTurn();
        dealer.makeTurn();
    }



    @Test
    public void testMakeRiver() throws Exception {
        List<Player> players = generatePlayers();
        Cards commonCards = new Cards();
        Dealer dealer = new Dealer(players, commonCards);
        dealer.makePreflop();
        dealer.makeFlop();
        dealer.makeTurn();
        dealer.makeRiver();
        assertEquals(5, commonCards.size());
    }

    @Test(expected = Exception.class)
    public void testMakeRiverExceptionThrowing1() throws Exception {
        Dealer dealer = generateDealer();
        dealer.makeRiver();
    }

    @Test(expected = Exception.class)
    public void testMakeRiverExceptionThrowing2() throws Exception {
        Dealer dealer = generateDealer();
        dealer.makePreflop();
        dealer.makeRiver();
    }

    @Test(expected = Exception.class)
    public void testMakeRiverExceptionThrowing3() throws Exception {
        Dealer dealer = generateDealer();
        dealer.makePreflop();
        dealer.makeFlop();
        dealer.makeRiver();
    }

    @Test(expected = Exception.class)
    public void testMakeRiverExceptionThrowing4() throws Exception {
        Dealer dealer = generateDealer();
        dealer.makePreflop();
        dealer.makeFlop();
        dealer.makeTurn();
        dealer.makeRiver();
        dealer.makeRiver();
    }

}
