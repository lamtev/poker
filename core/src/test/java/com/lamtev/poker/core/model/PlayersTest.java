package com.lamtev.poker.core.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class PlayersTest {

    private Player player = new Player("b", 100);

    @Test
    public void testDealer() {
        Players players = generatePlayers();
        assertEquals("a", players.dealer().getId());
    }

    @Test
    public void testSmallBlind() {
        Players players = generatePlayers();
        assertEquals("b", players.smallBlind().getId());
    }

    @Test
    public void testBigBlind() {
        Players players = generatePlayers();
        assertEquals("c", players.bigBlind().getId());
    }

    @Test
    public void testCurrent() {
        Players players = generatePlayers();
        players.nextAfterDealer();
        assertEquals("b", players.current().getId());

        players.nextAfterBigBlind();
        assertEquals("d", players.current().getId());

        List<String> ids = asList("e", "f");
        CardDeck cardDeck = new CardDeck();
        for (int i = 0; i < 2; ++i) {
            ids.forEach(id -> players.get(id).addCard(cardDeck.pickUpTop()));
        }
        ids.forEach(id -> players.get(id).fold());

        players.nextActive();
        assertEquals("g", players.current().getId());

        players.setLatestAggressor(player);
        assertEquals("b", players.current().getId());
    }

    @Test
    public void testActivePlayersNumber() {
        Players players = generatePlayers();
        List<String> ids = asList("e", "f");
        CardDeck cardDeck = new CardDeck();
        for (int i = 0; i < 2; ++i) {
            ids.forEach(id -> players.get(id).addCard(cardDeck.pickUpTop()));
        }
        ids.forEach(id -> players.get(id).fold());
        assertEquals(5, players.activePlayersNumber());
    }

    private Players generatePlayers() {

        List<Player> players = new ArrayList<>();
        players.addAll(asList(
                new Player("a", 100),
                player,
                new Player("c", 100),
                new Player("d", 100),
                new Player("e", 100),
                new Player("f", 100),
                new Player("g", 100)
        ));
        return new Players(players, "a");
    }

}