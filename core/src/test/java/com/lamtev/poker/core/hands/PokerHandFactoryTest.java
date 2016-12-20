package com.lamtev.poker.core.hands;

import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.model.Rank;
import com.lamtev.poker.core.model.Suit;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.lamtev.poker.core.hands.PokerHand.Name.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PokerHandFactoryTest {

    @Test
    public void testFlushCreation1() {
        Cards commonCards = new Cards() {{
            add(new Card(Rank.ACE, Suit.TILES));
            add(new Card(Rank.KING, Suit.HEARTS));
            add(new Card(Rank.TEN, Suit.PIKES));
            add(new Card(Rank.TWO, Suit.HEARTS));
            add(new Card(Rank.FIVE, Suit.HEARTS));
        }};
        PokerHandFactory pcf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(Rank.FOUR, Suit.HEARTS));
            add(new Card(Rank.EIGHT, Suit.HEARTS));
        }};
        PokerHand flush = pcf.createCombination(playerCards);
        assertEquals(FLUSH, flush.getName());
        assertTrue(flush.compareTo(new Flush(Rank.KING)) == 0);
    }

    @Test
    public void testFlushCreation2() {
        Cards commonCards = new Cards() {{
            add(new Card(Rank.JACK, Suit.HEARTS));
            add(new Card(Rank.ACE, Suit.TILES));
            add(new Card(Rank.TEN, Suit.PIKES));
            add(new Card(Rank.TWO, Suit.HEARTS));
            add(new Card(Rank.SEVEN, Suit.HEARTS));
        }};
        PokerHandFactory pcf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(Rank.FOUR, Suit.HEARTS));
            add(new Card(Rank.QUEEN, Suit.HEARTS));
        }};
        PokerHand flush = pcf.createCombination(playerCards);
        assertEquals(FLUSH, flush.getName());
        assertTrue(flush.compareTo(new Flush(Rank.QUEEN)) == 0);
    }

    @Test
    public void testStraightCreation1() {
        Cards commonCards = new Cards() {{
            add(new Card(Rank.JACK, Suit.HEARTS));
            add(new Card(Rank.ACE, Suit.TILES));
            add(new Card(Rank.TEN, Suit.PIKES));
            add(new Card(Rank.TWO, Suit.CLOVERS));
            add(new Card(Rank.KING, Suit.HEARTS));
        }};
        PokerHandFactory pcf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(Rank.FOUR, Suit.HEARTS));
            add(new Card(Rank.QUEEN, Suit.HEARTS));
        }};
        PokerHand straight = pcf.createCombination(playerCards);
        assertEquals(STRAIGHT, straight.getName());
        assertTrue(straight.compareTo(new Straight(Rank.ACE)) == 0);
    }

    @Test
    public void testStraightCreation2() {
        Cards commonCards = new Cards() {{
            add(new Card(Rank.THREE, Suit.HEARTS));
            add(new Card(Rank.ACE, Suit.TILES));
            add(new Card(Rank.FIVE, Suit.PIKES));
            add(new Card(Rank.TWO, Suit.CLOVERS));
            add(new Card(Rank.KING, Suit.HEARTS));
        }};
        PokerHandFactory pcf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(Rank.FOUR, Suit.HEARTS));
            add(new Card(Rank.QUEEN, Suit.HEARTS));
        }};
        PokerHand straight = pcf.createCombination(playerCards);
        assertEquals(STRAIGHT, straight.getName());
        assertTrue(straight.compareTo(new Straight(Rank.FIVE)) == 0);
    }

    @Test
    public void testStraightFlushCreation1() {
        Cards commonCards = new Cards() {{
            add(new Card(Rank.THREE, Suit.PIKES));
            add(new Card(Rank.ACE, Suit.PIKES));
            add(new Card(Rank.FIVE, Suit.PIKES));
            add(new Card(Rank.TWO, Suit.PIKES));
            add(new Card(Rank.KING, Suit.HEARTS));
        }};
        PokerHandFactory pcf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(Rank.FOUR, Suit.PIKES));
            add(new Card(Rank.QUEEN, Suit.HEARTS));
        }};
        PokerHand straightFlush = pcf.createCombination(playerCards);
        assertEquals(STRAIGHT_FLUSH, straightFlush.getName());
        assertTrue(straightFlush.compareTo(new StraightFlush(Rank.FIVE)) == 0);
    }

    @Test
    public void testStraightFlushCreation2() {
        Cards commonCards = new Cards() {{
            add(new Card(Rank.TEN, Suit.PIKES));
            add(new Card(Rank.ACE, Suit.TILES));
            add(new Card(Rank.JACK, Suit.PIKES));
            add(new Card(Rank.TWO, Suit.CLOVERS));
            add(new Card(Rank.KING, Suit.PIKES));
        }};
        PokerHandFactory pcf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(Rank.NINE, Suit.PIKES));
            add(new Card(Rank.QUEEN, Suit.PIKES));
        }};
        PokerHand straightFlush = pcf.createCombination(playerCards);
        assertEquals(STRAIGHT_FLUSH, straightFlush.getName());
        assertTrue(straightFlush.compareTo(new StraightFlush(Rank.KING)) == 0);
    }

    @Test
    public void testStraightFlushCreation3() {
        Cards commonCards = new Cards() {{
            add(new Card(Rank.TWO, Suit.PIKES));
            add(new Card(Rank.THREE, Suit.PIKES));
            add(new Card(Rank.FOUR, Suit.PIKES));
            add(new Card(Rank.FIVE, Suit.PIKES));
            add(new Card(Rank.SIX, Suit.PIKES));
        }};
        PokerHandFactory pcf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(Rank.SEVEN, Suit.PIKES));
            add(new Card(Rank.EIGHT, Suit.PIKES));
        }};
        PokerHand straightFlush = pcf.createCombination(playerCards);
        assertEquals(STRAIGHT_FLUSH, straightFlush.getName());
        assertTrue(straightFlush.compareTo(new StraightFlush(Rank.EIGHT)) == 0);
    }

    @Test
    public void testRoyalFlushCreation1() {
        Cards commonCards = new Cards() {{
            add(new Card(Rank.EIGHT, Suit.HEARTS));
            add(new Card(Rank.NINE, Suit.HEARTS));
            add(new Card(Rank.TEN, Suit.HEARTS));
            add(new Card(Rank.JACK, Suit.HEARTS));
            add(new Card(Rank.QUEEN, Suit.HEARTS));
        }};
        PokerHandFactory pcf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(Rank.KING, Suit.HEARTS));
            add(new Card(Rank.ACE, Suit.HEARTS));

        }};
        PokerHand royalFlush = pcf.createCombination(playerCards);
        assertEquals(ROYAL_FLUSH, royalFlush.getName());
        assertTrue(royalFlush.compareTo(new RoyalFlush()) == 0);
    }

    @Test
    public void testThreeOfAKindCreation1() {
        Cards commonCards = new Cards() {{
            add(new Card(Rank.ACE, Suit.PIKES));
            add(new Card(Rank.NINE, Suit.TILES));
            add(new Card(Rank.TEN, Suit.HEARTS));
            add(new Card(Rank.TWO, Suit.TILES));
            add(new Card(Rank.ACE, Suit.CLOVERS));
        }};
        PokerHandFactory pcf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(Rank.JACK, Suit.PIKES));
            add(new Card(Rank.ACE, Suit.HEARTS));

        }};
        PokerHand threeOfAKind = pcf.createCombination(playerCards);
        assertEquals(THREE_OF_A_KIND, threeOfAKind.getName());
        List<Rank> otherCardsRanks= new ArrayList<Rank>() {{
            add(Rank.JACK);
            add(Rank.TEN);
        }};
        assertTrue(threeOfAKind.compareTo(new ThreeOfAKind(Rank.ACE, otherCardsRanks)) == 0);
    }

    @Test
    public void testThreeOfAKindCreation2() {
        Cards commonCards = new Cards() {{
            add(new Card(Rank.ACE, Suit.PIKES));
            add(new Card(Rank.JACK, Suit.TILES));
            add(new Card(Rank.TEN, Suit.HEARTS));
            add(new Card(Rank.TWO, Suit.TILES));
            add(new Card(Rank.THREE, Suit.CLOVERS));
        }};
        PokerHandFactory pcf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(Rank.JACK, Suit.PIKES));
            add(new Card(Rank.JACK, Suit.HEARTS));

        }};
        PokerHand threeOfAKind = pcf.createCombination(playerCards);
        assertEquals(THREE_OF_A_KIND, threeOfAKind.getName());
        List<Rank> otherCardsRanks= new ArrayList<Rank>() {{
            add(Rank.ACE);
            add(Rank.TEN);
        }};
        assertTrue(threeOfAKind.compareTo(new ThreeOfAKind(Rank.JACK, otherCardsRanks)) == 0);
    }

    @Test
    public void testPairCreation1() {
        Cards commonCards = new Cards() {{
            add(new Card(Rank.TEN, Suit.PIKES));
            add(new Card(Rank.NINE, Suit.TILES));
            add(new Card(Rank.EIGHT, Suit.HEARTS));
            add(new Card(Rank.TWO, Suit.TILES));
            add(new Card(Rank.ACE, Suit.CLOVERS));
        }};
        PokerHandFactory pcf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(Rank.JACK, Suit.PIKES));
            add(new Card(Rank.ACE, Suit.HEARTS));

        }};
        PokerHand pair = pcf.createCombination(playerCards);
        assertEquals(PAIR, pair.getName());
        List<Rank> otherCardsRanks= new ArrayList<Rank>() {{
            add(Rank.JACK);
            add(Rank.TEN);
            add(Rank.NINE);
        }};
        assertTrue(pair.compareTo(new Pair(Rank.ACE, otherCardsRanks)) == 0);
    }

    @Test
    public void testPairCreation2() {
        Cards commonCards = new Cards() {{
            add(new Card(Rank.TEN, Suit.PIKES));
            add(new Card(Rank.FOUR, Suit.TILES));
            add(new Card(Rank.EIGHT, Suit.HEARTS));
            add(new Card(Rank.TWO, Suit.TILES));
            add(new Card(Rank.THREE, Suit.CLOVERS));
        }};
        PokerHandFactory pcf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(Rank.JACK, Suit.PIKES));
            add(new Card(Rank.JACK, Suit.HEARTS));
        }};
        PokerHand pair = pcf.createCombination(playerCards);
        assertEquals(PAIR, pair.getName());
        List<Rank> otherCardsRanks= new ArrayList<Rank>() {{
           add(Rank.TEN);
           add(Rank.EIGHT);
           add(Rank.FOUR);
        }};
        assertTrue(pair.compareTo(new Pair(Rank.JACK, otherCardsRanks)) == 0);
    }

    @Test
    public void testTwoPairsCreation1() {
        Cards commonCards = new Cards() {{
            add(new Card(Rank.TEN, Suit.PIKES));
            add(new Card(Rank.JACK, Suit.TILES));
            add(new Card(Rank.EIGHT, Suit.HEARTS));
            add(new Card(Rank.TWO, Suit.TILES));
            add(new Card(Rank.THREE, Suit.CLOVERS));
        }};
        PokerHandFactory phf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(Rank.TWO, Suit.PIKES));
            add(new Card(Rank.JACK, Suit.HEARTS));
        }};
        PokerHand twoPairs = phf.createCombination(playerCards);
        assertEquals(TWO_PAIRS, twoPairs.getName());
        assertTrue(twoPairs.compareTo(new TwoPairs(Rank.JACK, Rank.TWO, Rank.TEN)) == 0);
    }

    @Test
    public void testTwoPairsCreation2() {
        Cards commonCards = new Cards() {{
            add(new Card(Rank.TEN, Suit.PIKES));
            add(new Card(Rank.KING, Suit.TILES));
            add(new Card(Rank.TWO, Suit.HEARTS));
            add(new Card(Rank.TWO, Suit.TILES));
            add(new Card(Rank.NINE, Suit.CLOVERS));
        }};
        PokerHandFactory phf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(Rank.THREE, Suit.PIKES));
            add(new Card(Rank.KING, Suit.HEARTS));
        }};
        PokerHand twoPairs = phf.createCombination(playerCards);
        assertEquals(TWO_PAIRS, twoPairs.getName());
        assertTrue(twoPairs.compareTo(new TwoPairs(Rank.KING, Rank.TWO, Rank.TEN)) == 0);
    }

    @Test
    public void testFourOfAKindCreation1() {
        Cards commonCards = new Cards() {{
            add(new Card(Rank.TEN, Suit.PIKES));
            add(new Card(Rank.KING, Suit.TILES));
            add(new Card(Rank.TWO, Suit.HEARTS));
            add(new Card(Rank.TWO, Suit.TILES));
            add(new Card(Rank.TWO, Suit.CLOVERS));
        }};
        PokerHandFactory phf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(Rank.TWO, Suit.PIKES));
            add(new Card(Rank.KING, Suit.HEARTS));
        }};
        PokerHand fourOfAKind = phf.createCombination(playerCards);
        assertEquals(FOUR_OF_A_KIND, fourOfAKind.getName());
        assertTrue(fourOfAKind.compareTo(new FourOfAKind(Rank.TWO, Rank.KING)) == 0);
    }

    @Test
    public void testFourOfAKindCreation2() {
        Cards commonCards = new Cards() {{
            add(new Card(Rank.FOUR, Suit.PIKES));
            add(new Card(Rank.ACE, Suit.TILES));
            add(new Card(Rank.FOUR, Suit.HEARTS));
            add(new Card(Rank.FOUR, Suit.TILES));
            add(new Card(Rank.FOUR, Suit.CLOVERS));
        }};
        PokerHandFactory phf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(Rank.TWO, Suit.PIKES));
            add(new Card(Rank.KING, Suit.HEARTS));
        }};
        PokerHand fourOfAKind = phf.createCombination(playerCards);
        assertEquals(FOUR_OF_A_KIND, fourOfAKind.getName());
        assertTrue(fourOfAKind.compareTo(new FourOfAKind(Rank.FOUR, Rank.ACE)) == 0);
    }

    @Test
    public void testFullHouseCreation1() {
        Cards commonCards = new Cards() {{
            add(new Card(Rank.FOUR, Suit.PIKES));
            add(new Card(Rank.KING, Suit.TILES));
            add(new Card(Rank.FOUR, Suit.HEARTS));
            add(new Card(Rank.FOUR, Suit.TILES));
            add(new Card(Rank.TWO, Suit.CLOVERS));
        }};
        PokerHandFactory pcf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(Rank.TWO, Suit.PIKES));
            add(new Card(Rank.KING, Suit.HEARTS));
        }};
        PokerHand fullHouse = pcf.createCombination(playerCards);
        assertEquals(FULL_HOUSE, fullHouse.getName());
        assertTrue(fullHouse.compareTo(new FullHouse(Rank.FOUR, Rank.KING)) == 0);
    }

    @Test
    public void testFullHouseCreation2() {
        Cards commonCards = new Cards() {{
            add(new Card(Rank.TWO, Suit.PIKES));
            add(new Card(Rank.FIVE, Suit.TILES));
            add(new Card(Rank.FIVE, Suit.HEARTS));
            add(new Card(Rank.FOUR, Suit.TILES));
            add(new Card(Rank.THREE, Suit.CLOVERS));
        }};
        PokerHandFactory pcf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(Rank.FOUR, Suit.PIKES));
            add(new Card(Rank.FOUR, Suit.CLOVERS));
        }};
        PokerHand fullHouse = pcf.createCombination(playerCards);
        assertEquals(FULL_HOUSE, fullHouse.getName());
        assertTrue(fullHouse.compareTo(new FullHouse(Rank.FOUR, Rank.FIVE)) == 0);
    }

}