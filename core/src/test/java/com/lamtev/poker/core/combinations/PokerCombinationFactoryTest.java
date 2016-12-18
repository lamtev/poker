package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Rank;
import com.lamtev.poker.core.model.Suit;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.lamtev.poker.core.combinations.PokerCombination.Name.FLUSH;
import static com.lamtev.poker.core.combinations.PokerCombination.Name.STRAIGHT;
import static com.lamtev.poker.core.combinations.PokerCombination.Name.STRAIGHT_FLUSH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PokerCombinationFactoryTest {

    @Test
    public void testFlushCreation1() {
        List<Card> commonCards = new ArrayList<Card>() {{
            add(new Card(Rank.ACE, Suit.TILES));
            add(new Card(Rank.KING, Suit.HEARTS));
            add(new Card(Rank.TEN, Suit.PIKES));
            add(new Card(Rank.TWO, Suit.HEARTS));
            add(new Card(Rank.FIVE, Suit.HEARTS));
        }};
        PokerCombinationFactory pcf = new PokerCombinationFactory(commonCards);
        List<Card> playerCards = new ArrayList<Card>() {{
            add(new Card(Rank.FOUR, Suit.HEARTS));
            add(new Card(Rank.EIGHT, Suit.HEARTS));
        }};
        PokerCombination flush = pcf.createCombination(playerCards);
        assertEquals(FLUSH, flush.getName());
        assertTrue(flush.compareTo(new Flush(Rank.KING)) == 0);
    }

    @Test
    public void testFlushCreation2() {
        List<Card> commonCards = new ArrayList<Card>() {{
            add(new Card(Rank.JACK, Suit.HEARTS));
            add(new Card(Rank.ACE, Suit.TILES));
            add(new Card(Rank.TEN, Suit.PIKES));
            add(new Card(Rank.TWO, Suit.HEARTS));
            add(new Card(Rank.SEVEN, Suit.HEARTS));
        }};
        PokerCombinationFactory pcf = new PokerCombinationFactory(commonCards);
        List<Card> playerCards = new ArrayList<Card>() {{
            add(new Card(Rank.FOUR, Suit.HEARTS));
            add(new Card(Rank.QUEEN, Suit.HEARTS));
        }};
        PokerCombination flush = pcf.createCombination(playerCards);
        assertEquals(FLUSH, flush.getName());
        assertTrue(flush.compareTo(new Flush(Rank.QUEEN)) == 0);
    }

    @Test
    public void testStraightCreation1() {
        List<Card> commonCards = new ArrayList<Card>() {{
            add(new Card(Rank.JACK, Suit.HEARTS));
            add(new Card(Rank.ACE, Suit.TILES));
            add(new Card(Rank.TEN, Suit.PIKES));
            add(new Card(Rank.TWO, Suit.CLOVERS));
            add(new Card(Rank.KING, Suit.HEARTS));
        }};
        PokerCombinationFactory pcf = new PokerCombinationFactory(commonCards);
        List<Card> playerCards = new ArrayList<Card>() {{
            add(new Card(Rank.FOUR, Suit.HEARTS));
            add(new Card(Rank.QUEEN, Suit.HEARTS));
        }};
        PokerCombination straight = pcf.createCombination(playerCards);
        assertEquals(STRAIGHT, straight.getName());
        assertTrue(straight.compareTo(new Straight(Rank.ACE)) == 0);
    }

    @Test
    public void testStraightCreation2() {
        List<Card> commonCards = new ArrayList<Card>() {{
            add(new Card(Rank.THREE, Suit.HEARTS));
            add(new Card(Rank.ACE, Suit.TILES));
            add(new Card(Rank.FIVE, Suit.PIKES));
            add(new Card(Rank.TWO, Suit.CLOVERS));
            add(new Card(Rank.KING, Suit.HEARTS));
        }};
        PokerCombinationFactory pcf = new PokerCombinationFactory(commonCards);
        List<Card> playerCards = new ArrayList<Card>() {{
            add(new Card(Rank.FOUR, Suit.HEARTS));
            add(new Card(Rank.QUEEN, Suit.HEARTS));
        }};
        PokerCombination straight = pcf.createCombination(playerCards);
        assertEquals(STRAIGHT, straight.getName());
        assertTrue(straight.compareTo(new Straight(Rank.FIVE)) == 0);
    }

    @Test
    public void testStraightFlushCreation1() {
        List<Card> commonCards = new ArrayList<Card>() {{
            add(new Card(Rank.THREE, Suit.PIKES));
            add(new Card(Rank.ACE, Suit.PIKES));
            add(new Card(Rank.FIVE, Suit.PIKES));
            add(new Card(Rank.TWO, Suit.PIKES));
            add(new Card(Rank.KING, Suit.HEARTS));
        }};
        PokerCombinationFactory pcf = new PokerCombinationFactory(commonCards);
        List<Card> playerCards = new ArrayList<Card>() {{
            add(new Card(Rank.FOUR, Suit.PIKES));
            add(new Card(Rank.QUEEN, Suit.HEARTS));
        }};
        PokerCombination straightFlush = pcf.createCombination(playerCards);
        assertEquals(STRAIGHT_FLUSH, straightFlush.getName());
        assertTrue(straightFlush.compareTo(new StraightFlush(Rank.FIVE)) == 0);
    }

    @Test
    public void testStraightFlushCreation2() {
        List<Card> commonCards = new ArrayList<Card>() {{
            add(new Card(Rank.TEN, Suit.PIKES));
            add(new Card(Rank.ACE, Suit.TILES));
            add(new Card(Rank.JACK, Suit.PIKES));
            add(new Card(Rank.TWO, Suit.CLOVERS));
            add(new Card(Rank.KING, Suit.PIKES));
        }};
        PokerCombinationFactory pcf = new PokerCombinationFactory(commonCards);
        List<Card> playerCards = new ArrayList<Card>() {{
            add(new Card(Rank.NINE, Suit.PIKES));
            add(new Card(Rank.QUEEN, Suit.PIKES));
        }};
        PokerCombination straightFlush = pcf.createCombination(playerCards);
        assertEquals(STRAIGHT_FLUSH, straightFlush.getName());
        assertTrue(straightFlush.compareTo(new StraightFlush(Rank.KING)) == 0);
    }

    @Test
    public void testStraightFlushCreation3() {
        List<Card> commonCards = new ArrayList<Card>() {{
            add(new Card(Rank.TWO, Suit.PIKES));
            add(new Card(Rank.THREE, Suit.PIKES));
            add(new Card(Rank.FOUR, Suit.PIKES));
            add(new Card(Rank.FIVE, Suit.PIKES));
            add(new Card(Rank.SIX, Suit.PIKES));
        }};
        PokerCombinationFactory pcf = new PokerCombinationFactory(commonCards);
        List<Card> playerCards = new ArrayList<Card>() {{
            add(new Card(Rank.SEVEN, Suit.PIKES));
            add(new Card(Rank.EIGHT, Suit.PIKES));
        }};
        PokerCombination straightFlush = pcf.createCombination(playerCards);
        assertEquals(STRAIGHT_FLUSH, straightFlush.getName());
        assertTrue(straightFlush.compareTo(new StraightFlush(Rank.EIGHT)) == 0);
    }

}