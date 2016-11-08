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

    @Test
    public void testMakePreflop() throws Exception {
        List<Player> players = generatePlayers();
        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        for (Player player : players) {
            assertEquals(2, player.getCards().size());
        }
    }

    @Test(expected = Exception.class)
    public void testMakePreflopExceptionThrowing() throws Exception {
        List<Player> players = generatePlayers();
        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        dealer.makePreflop();
    }

    @Test
    public void testMakeFlop() throws Exception {
        List<Player> players = generatePlayers();
        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        dealer.makeFlop();
        assertEquals(3, dealer.getCommonCards().size());
    }

    @Test(expected = Exception.class)
    public void testMakeFlopExceptionThrowing1() throws Exception {
        List<Player> players = generatePlayers();
        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        dealer.makeFlop();
        dealer.makeFlop();
    }

    @Test(expected = Exception.class)
    public void testMakeFlopExceptionThrowing2() throws Exception {
        List<Player> players = generatePlayers();
        Dealer dealer = new Dealer(players);
        dealer.makeFlop();
    }

    @Test
    public void testMakeTurn() throws Exception {
        List<Player> players = generatePlayers();
        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        dealer.makeFlop();
        dealer.makeTurn();
        assertEquals(4, dealer.getCommonCards().size());
    }

    @Test(expected = Exception.class)
    public void testMakeTurnExceptionThrowing1() throws Exception {
        List<Player> players = generatePlayers();
        Dealer dealer = new Dealer(players);
        dealer.makeTurn();
    }

    @Test(expected = Exception.class)
    public void testMakeTurnExceptionThrowing2() throws Exception {
        List<Player> players = generatePlayers();
        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        dealer.makeTurn();
    }

    @Test(expected = Exception.class)
    public void testMakeTurnExceptionThrowing3() throws Exception {
        List<Player> players = generatePlayers();
        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        dealer.makeFlop();
        dealer.makeTurn();
        dealer.makeTurn();
    }



    @Test
    public void testMakeRiver() throws Exception {
        List<Player> players = generatePlayers();
        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        dealer.makeFlop();
        dealer.makeTurn();
        dealer.makeRiver();

        assertEquals(5, dealer.getCommonCards().size());
    }

    @Test(expected = Exception.class)
    public void testMakeRiverExceptionThrowing1() throws Exception {
        List<Player> players = generatePlayers();
        Dealer dealer = new Dealer(players);
        dealer.makeRiver();
    }

    @Test(expected = Exception.class)
    public void testMakeRiverExceptionThrowing2() throws Exception {
        List<Player> players = generatePlayers();
        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        dealer.makeRiver();
    }

    @Test(expected = Exception.class)
    public void testMakeRiverExceptionThrowing3() throws Exception {
        List<Player> players = generatePlayers();
        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        dealer.makeFlop();
        dealer.makeRiver();
    }

    @Test(expected = Exception.class)
    public void testMakeRiverExceptionThrowing4() throws Exception {
        List<Player> players = generatePlayers();
        Dealer dealer = new Dealer(players);
        dealer.makePreflop();
        dealer.makeFlop();
        dealer.makeTurn();
        dealer.makeRiver();
        dealer.makeRiver();
    }

}
