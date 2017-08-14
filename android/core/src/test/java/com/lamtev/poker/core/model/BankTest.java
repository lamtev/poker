package com.lamtev.poker.core.model;

import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.hands.PokerHandFactory;
import org.junit.Test;

import java.util.*;

import static com.lamtev.poker.core.model.Rank.*;
import static com.lamtev.poker.core.model.Suit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BankTest {

    @Test
    public void testAcceptBlindWagers() {
        Players players = generatePlayers();
        Bank bank = new Bank(players);
        bank.acceptBlindWagers(5);
        assertEquals(5, players.get("a").wager());
        assertEquals(95, players.get("a").stack());
        assertEquals(10, players.get("b").wager());
        assertEquals(190, players.get("b").stack());
        assertEquals(15, bank.money());
        assertEquals(10, bank.wager());
    }

    @Test
    public void testAcceptCall() throws Exception {
        Players players = generatePlayers();
        Bank bank = new Bank(players);
        bank.acceptBlindWagers(5);
        bank.acceptCall(players.get("c"));

        assertEquals(10, players.get("c").wager());
        assertEquals(140, players.get("c").stack());
        assertEquals(25, bank.money());
        assertEquals(10, bank.wager());
    }

    @Test
    public void testAcceptRaise() throws Exception {
        Players players = generatePlayers();
        Bank bank = new Bank(players);
        bank.acceptBlindWagers(5);
        bank.acceptRaise(30, players.get("d"));

        assertEquals(40, players.get("d").wager());
        assertEquals(260, players.get("d").stack());
        assertEquals(55, bank.money());
        assertEquals(40, bank.wager());

        bank.acceptRaise(20, players.get("e"));

        assertEquals(60, players.get("e").wager());
        assertEquals(190, players.get("e").stack());
        assertEquals(115, bank.money());
        assertEquals(60, bank.wager());

        bank.acceptRaise(30, players.get("d"));

        assertEquals(90, players.get("d").wager());
        assertEquals(210, players.get("d").stack());
        assertEquals(165, bank.money());
        assertEquals(90, bank.wager());

    }

    @Test
    public void testAcceptAllIn() {
        Players players = generatePlayers();
        Bank bank = new Bank(players);

        bank.acceptAllIn(players.get("d"));
        assertEquals(0, players.get("d").stack());
        assertEquals(300, bank.money());
        assertEquals(300, bank.wager());
    }

    @Test
    public void testGiveMoneyToWinners() throws Exception {
        final Players players = generatePlayersWithCards();
        Bank bank = new Bank(players);
        bank.acceptBlindWagers(50);
        bank.acceptCall(players.get("c"));
        bank.acceptCall(players.get("d"));
        bank.acceptCall(players.get("e"));
        bank.acceptCall(players.get("a"));

        bank.acceptAllIn(players.get("c"));
        bank.acceptCall(players.get("b"));
        bank.acceptCall(players.get("d"));
        bank.acceptCall(players.get("e"));

        bank.acceptRaise(40, players.get("b"));
        bank.acceptCall(players.get("d"));
        bank.acceptCall(players.get("e"));

        final PokerHandFactory phf = new PokerHandFactory(generateCommunityCards());
        Map<Player, PokerHand> showedDownPlayers = new HashMap<Player, PokerHand>() {{
            put(players.get("a"), phf.createCombination(players.get("a").cards()));
            put(players.get("b"), phf.createCombination(players.get("b").cards()));
            put(players.get("c"), phf.createCombination(players.get("c").cards()));
            put(players.get("d"), phf.createCombination(players.get("d").cards()));
            put(players.get("e"), phf.createCombination(players.get("e").cards()));
        }};

        Set<Player> winners = bank.giveMoneyToWinners(showedDownPlayers);
        assertEquals(2, winners.size());
        Player winner1 = players.get("c");
        Player winner2 = players.get("e");
        assertTrue(winners.contains(winner1));
        assertTrue(winners.contains(winner2));
        assertEquals(700, winner1.stack());
        assertEquals(180, winner2.stack());
        assertEquals(0, players.get("a").stack());
        assertEquals(10, players.get("b").stack());
        assertEquals(110, players.get("d").stack());
    }

    private Players generatePlayersWithCards() {
        Players players = generatePlayers();
        Queue<Card> cards = generateHoleCards();
        for (int i = 0; i < 2; ++i) {
            for (Player player : players) {
                player.addCard(cards.poll());
            }
        }
        System.out.println(players);
        return players;
    }

    private Players generatePlayers() {
        List<Player> players = new ArrayList<>();
        players.add(new Player("a", 100));
        players.add(new Player("b", 200));
        players.add(new Player("c", 150));
        players.add(new Player("d", 300));
        players.add(new Player("e", 250));
        return new Players(players, "e");
    }

    private Queue<Card> generateHoleCards() {
        Queue<Card> cards = new ArrayDeque<>();
        cards.add(new Card(QUEEN, PIKES));
        cards.add(new Card(TWO, CLOVERS));
        cards.add(new Card(KING, CLOVERS));
        cards.add(new Card(ACE, TILES));
        cards.add(new Card(EIGHT, CLOVERS));
        cards.add(new Card(JACK, PIKES));
        cards.add(new Card(NINE, HEARTS));
        cards.add(new Card(SEVEN, TILES));
        cards.add(new Card(ACE, HEARTS));
        cards.add(new Card(THREE, PIKES));
        return cards;
    }

    private Cards generateCommunityCards() {
        Cards cards = new Cards();
        cards.add(new Card(QUEEN, CLOVERS));
        cards.add(new Card(JACK, CLOVERS));
        cards.add(new Card(TWO, TILES));
        cards.add(new Card(NINE, CLOVERS));
        cards.add(new Card(TEN, CLOVERS));
        return cards;
    }

}
