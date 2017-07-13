package com.lamtev.poker.core.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BankTest {

    private Players generatePlayers() {
        List<Player> players = new ArrayList<>();
        players.add(new Player("a", 100));
        players.add(new Player("b", 200));
        players.add(new Player("c", 150));
        players.add(new Player("d", 300));
        players.add(new Player("e", 250));
        return new Players(players, "e");
    }

    //TODO add cases for exceptions

    @Test
    public void testAcceptBlindWagers() {
        Players players = generatePlayers();
        Bank bank = new Bank(players);
        bank.acceptBlindWagers(5);
        assertEquals(5, players.get(0).wager());
        assertEquals(95, players.get(0).stack());
        assertEquals(10, players.get(1).wager());
        assertEquals(190, players.get(1).stack());
        assertEquals(15, bank.money());
        assertEquals(10, bank.currentWager());
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
        assertEquals(10, players.get(2).wager());
        assertEquals(140, players.get(2).stack());
        assertEquals(25, bank.money());
        assertEquals(10, bank.currentWager());
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
        assertEquals(40, players.get(3).wager());
        assertEquals(260, players.get(3).stack());
        assertEquals(55, bank.money());
        assertEquals(40, bank.currentWager());

        try {
            bank.acceptRaiseFromPlayer(20, players.get(4));
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(60, players.get(4).wager());
        assertEquals(190, players.get(4).stack());
        assertEquals(115, bank.money());
        assertEquals(60, bank.currentWager());

        try {
            bank.acceptRaiseFromPlayer(30, players.get(3));
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(90, players.get(3).wager());
        assertEquals(210, players.get(3).stack());
        assertEquals(165, bank.money());
        assertEquals(90, bank.currentWager());

    }

}
