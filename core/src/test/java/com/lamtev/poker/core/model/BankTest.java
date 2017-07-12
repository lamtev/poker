package com.lamtev.poker.core.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {

    private Players generatePlayers() {
        Players players = new Players();
        players.add(new Player("a", 100));
        players.add(new Player("b", 200));
        players.add(new Player("c", 150));
        players.add(new Player("d", 300));
        players.add(new Player("e", 250));
        players.setDealer("e");
        return players;
    }

    //TODO add cases for exceptions

    @Test
    public void testAcceptBlindWagers() {
        Players players = generatePlayers();
        Bank bank = new Bank(players);
        bank.acceptBlindWagers(5);
        assertEquals(5, players.get(0).getWager());
        assertEquals(95, players.get(0).getStack());
        assertEquals(10, players.get(1).getWager());
        assertEquals(190, players.get(1).getStack());
        assertEquals(15, bank.getMoney());
        assertEquals(10, bank.getCurrentWager());
    }

    @Test
    public void testAcceptCallFromPlayer() {
        Players players = generatePlayers();
        Bank bank = new Bank(players);
        bank.acceptBlindWagers(5);
        try {
            bank.acceptCallFromPlayer(players.get(2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(10, players.get(2).getWager());
        assertEquals(140, players.get(2).getStack());
        assertEquals(25, bank.getMoney());
        assertEquals(10, bank.getCurrentWager());
    }

    @Test
    public void testAcceptRaiseFromPlayer() {
        Players players = generatePlayers();
        Bank bank = new Bank(players);
        bank.acceptBlindWagers(5);
        try {
            bank.acceptRaiseFromPlayer(30, players.get(3));
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(40, players.get(3).getWager());
        assertEquals(260, players.get(3).getStack());
        assertEquals(55, bank.getMoney());
        assertEquals(40, bank.getCurrentWager());

        try {
            bank.acceptRaiseFromPlayer(20, players.get(4));
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(60, players.get(4).getWager());
        assertEquals(190, players.get(4).getStack());
        assertEquals(115, bank.getMoney());
        assertEquals(60, bank.getCurrentWager());

        try {
            bank.acceptRaiseFromPlayer(30, players.get(3));
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(90, players.get(3).getWager());
        assertEquals(210, players.get(3).getStack());
        assertEquals(165, bank.getMoney());
        assertEquals(90, bank.getCurrentWager());

    }

}
