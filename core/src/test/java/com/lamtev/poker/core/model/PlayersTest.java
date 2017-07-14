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
        assertEquals("a", players.dealer().id());
    }

    @Test
    public void testSmallBlind() {
        Players players = generatePlayers();
        assertEquals("b", players.smallBlind().id());
    }

    @Test
    public void testBigBlind() {
        Players players = generatePlayers();
        assertEquals("c", players.bigBlind().id());
    }

    @Test
    public void testCurrent() {
        Players players = generatePlayers();
        players.nextNonAllinnerAfterDealer();
        assertEquals("b", players.current().id());

        players.nextAfterBigBlind();
        assertEquals("d", players.current().id());

        List<String> ids = asList("e", "f");
        CardDeck cardDeck = new CardDeck();
        for (int i = 0; i < 2; ++i) {
            ids.forEach(id -> players.get(id).addCard(cardDeck.pickUpTop()));
        }
        ids.forEach(id -> players.get(id).fold());

        players.nextActive();
        assertEquals("g", players.current().id());

        players.setLatestAggressor(player);
        assertEquals("b", players.current().id());
    }

    @Test
    public void testNextActiveNonAllinner() {
        Players players = generatePlayers();
        players.nextActiveNonAllinner();
        players.nextActiveNonAllinner();
        players.nextActiveNonAllinner();
        assertEquals("f", players.current().id());
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
                new Player("d", 0),
                new Player("e", 0),
                new Player("f", 100),
                new Player("g", 100)
        ));
        return new Players(players, "a");
    }

}