package com.lamtev.poker.core.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DealerTest {

    private Players generatePlayers() {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            players.add(new Player("pro" + i, 100));
        }
        return new Players(players, "pro4");
    }

    private Dealer generateDealer() {
        return new Dealer(
                generatePlayers(),
                new Cards()
        );
    }

    @Test
    public void testMakePreflop() {
        Players players = generatePlayers();
        Dealer dealer = new Dealer(players, new Cards());
        dealer.makePreflop();
        for (Player player : players) {
            assertEquals(2, player.cards().size());
        }
    }

    @Test(expected = Exception.class)
    public void testMakePreflopExceptionThrowing() {
        Dealer dealer = generateDealer();
        dealer.makePreflop();
        dealer.makePreflop();
    }

    @Test
    public void testMakeFlop() {
        Players players = generatePlayers();
        Cards commonCards = new Cards();
        Dealer dealer = new Dealer(players, commonCards);
        dealer.makePreflop();
        dealer.makeFlop();
        assertEquals(3, commonCards.size());
    }

    @Test(expected = Exception.class)
    public void testMakeFlopExceptionThrowing1() {
        Dealer dealer = generateDealer();
        dealer.makePreflop();
        dealer.makeFlop();
        dealer.makeFlop();
    }

    @Test(expected = Exception.class)
    public void testMakeFlopExceptionThrowing2() {
        Dealer dealer = generateDealer();
        dealer.makeFlop();
    }

    @Test
    public void testMakeTurn() throws Exception {
        Players players = generatePlayers();
        Cards commonCards = new Cards();
        Dealer dealer = new Dealer(players, commonCards);
        dealer.makePreflop();
        dealer.makeFlop();
        dealer.makeTurn();
        assertEquals(4, commonCards.size());
    }

    @Test(expected = Exception.class)
    public void testMakeTurnExceptionThrowing1() {
        Dealer dealer = generateDealer();
        dealer.makeTurn();
    }

    @Test(expected = Exception.class)
    public void testMakeTurnExceptionThrowing2() {
        Dealer dealer = generateDealer();
        dealer.makePreflop();
        dealer.makeTurn();
    }

    @Test(expected = Exception.class)
    public void testMakeTurnExceptionThrowing3() {
        Dealer dealer = generateDealer();
        dealer.makePreflop();
        dealer.makeFlop();
        dealer.makeTurn();
        dealer.makeTurn();
    }


    @Test
    public void testMakeRiver() {
        Players players = generatePlayers();
        Cards commonCards = new Cards();
        Dealer dealer = new Dealer(players, commonCards);
        dealer.makePreflop();
        dealer.makeFlop();
        dealer.makeTurn();
        dealer.makeRiver();
        assertEquals(5, commonCards.size());
    }

    @Test(expected = Exception.class)
    public void testMakeRiverExceptionThrowing1() {
        Dealer dealer = generateDealer();
        dealer.makeRiver();
    }

    @Test(expected = Exception.class)
    public void testMakeRiverExceptionThrowing2() {
        Dealer dealer = generateDealer();
        dealer.makePreflop();
        dealer.makeRiver();
    }

    @Test(expected = Exception.class)
    public void testMakeRiverExceptionThrowing3() {
        Dealer dealer = generateDealer();
        dealer.makePreflop();
        dealer.makeFlop();
        dealer.makeRiver();
    }

    @Test(expected = Exception.class)
    public void testMakeRiverExceptionThrowing4() {
        Dealer dealer = generateDealer();
        dealer.makePreflop();
        dealer.makeFlop();
        dealer.makeTurn();
        dealer.makeRiver();
        dealer.makeRiver();
    }

}
