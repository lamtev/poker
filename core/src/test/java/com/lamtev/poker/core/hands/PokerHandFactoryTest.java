package com.lamtev.poker.core.hands;

import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.model.Rank;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.lamtev.poker.core.hands.PokerHand.Name.*;
import static com.lamtev.poker.core.model.Rank.*;
import static com.lamtev.poker.core.model.Suit.*;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PokerHandFactoryTest {

    @Test
    public void testRoyalFlushCreation1() {
        Cards commonCards = new Cards() {{
            add(new Card(TWO, HEARTS));
            add(new Card(NINE, HEARTS));
            add(new Card(TEN, HEARTS));
            add(new Card(JACK, HEARTS));
            add(new Card(QUEEN, HEARTS));
        }};
        PokerHandFactory pcf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(KING, HEARTS));
            add(new Card(ACE, HEARTS));

        }};
        PokerHand royalFlush = pcf.createCombination(playerCards);
        assertEquals(ROYAL_FLUSH, royalFlush.getName());
        assertTrue(royalFlush.compareTo(new RoyalFlush()) == 0);
    }

    @Test
    public void testStraightFlushCreation1() {
        Cards commonCards = new Cards() {{
            add(new Card(THREE, PIKES));
            add(new Card(ACE, PIKES));
            add(new Card(FIVE, PIKES));
            add(new Card(TWO, PIKES));
            add(new Card(KING, HEARTS));
        }};
        PokerHandFactory pcf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(FOUR, PIKES));
            add(new Card(QUEEN, HEARTS));
        }};
        PokerHand straightFlush = pcf.createCombination(playerCards);
        assertEquals(STRAIGHT_FLUSH, straightFlush.getName());
        assertTrue(straightFlush.compareTo(new StraightFlush(FIVE)) == 0);
    }

    @Test
    public void testStraightFlushCreation2() {
        Cards commonCards = new Cards() {{
            add(new Card(TEN, PIKES));
            add(new Card(ACE, TILES));
            add(new Card(JACK, PIKES));
            add(new Card(TWO, CLOVERS));
            add(new Card(KING, PIKES));
        }};
        PokerHandFactory pcf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(NINE, PIKES));
            add(new Card(QUEEN, PIKES));
        }};
        PokerHand straightFlush = pcf.createCombination(playerCards);
        assertEquals(STRAIGHT_FLUSH, straightFlush.getName());
        assertTrue(straightFlush.compareTo(new StraightFlush(KING)) == 0);
    }

    @Test
    public void testStraightFlushCreation3() {
        Cards commonCards = new Cards() {{
            add(new Card(TWO, PIKES));
            add(new Card(THREE, PIKES));
            add(new Card(FOUR, PIKES));
            add(new Card(FIVE, PIKES));
            add(new Card(SIX, PIKES));
        }};
        PokerHandFactory pcf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(SEVEN, PIKES));
            add(new Card(EIGHT, PIKES));
        }};
        PokerHand straightFlush = pcf.createCombination(playerCards);
        assertEquals(STRAIGHT_FLUSH, straightFlush.getName());
        assertTrue(straightFlush.compareTo(new StraightFlush(EIGHT)) == 0);
    }

    @Test
    public void testFourOfAKindCreation1() {
        Cards commonCards = new Cards() {{
            add(new Card(TEN, PIKES));
            add(new Card(KING, TILES));
            add(new Card(TWO, HEARTS));
            add(new Card(TWO, TILES));
            add(new Card(TWO, CLOVERS));
        }};
        PokerHandFactory phf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(TWO, PIKES));
            add(new Card(KING, HEARTS));
        }};
        PokerHand fourOfAKind = phf.createCombination(playerCards);
        assertEquals(FOUR_OF_A_KIND, fourOfAKind.getName());
        assertTrue(fourOfAKind.compareTo(new FourOfAKind(TWO, KING)) == 0);
    }

    @Test
    public void testFourOfAKindCreation2() {
        Cards commonCards = new Cards() {{
            add(new Card(FOUR, PIKES));
            add(new Card(ACE, TILES));
            add(new Card(FOUR, HEARTS));
            add(new Card(FOUR, TILES));
            add(new Card(FOUR, CLOVERS));
        }};
        PokerHandFactory phf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(TWO, PIKES));
            add(new Card(KING, HEARTS));
        }};
        PokerHand fourOfAKind = phf.createCombination(playerCards);
        assertEquals(FOUR_OF_A_KIND, fourOfAKind.getName());
        assertTrue(fourOfAKind.compareTo(new FourOfAKind(FOUR, ACE)) == 0);
    }

    @Test
    public void testFullHouseCreation1() {
        Cards commonCards = new Cards() {{
            add(new Card(FOUR, PIKES));
            add(new Card(KING, TILES));
            add(new Card(FOUR, HEARTS));
            add(new Card(FOUR, TILES));
            add(new Card(TWO, CLOVERS));
        }};
        PokerHandFactory pcf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(TWO, PIKES));
            add(new Card(KING, HEARTS));
        }};
        PokerHand fullHouse = pcf.createCombination(playerCards);
        assertEquals(FULL_HOUSE, fullHouse.getName());
        assertTrue(fullHouse.compareTo(new FullHouse(FOUR, KING)) == 0);
    }

    @Test
    public void testFullHouseCreation2() {
        Cards commonCards = new Cards() {{
            add(new Card(TWO, PIKES));
            add(new Card(FIVE, TILES));
            add(new Card(FIVE, HEARTS));
            add(new Card(FOUR, TILES));
            add(new Card(THREE, CLOVERS));
        }};
        PokerHandFactory pcf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(FOUR, PIKES));
            add(new Card(FOUR, CLOVERS));
        }};
        PokerHand fullHouse = pcf.createCombination(playerCards);
        assertEquals(FULL_HOUSE, fullHouse.getName());
        assertTrue(fullHouse.compareTo(new FullHouse(FOUR, FIVE)) == 0);
    }

    @Test
    public void testFlushCreation1() {
        Cards commonCards = new Cards() {{
            add(new Card(ACE, TILES));
            add(new Card(KING, HEARTS));
            add(new Card(TEN, PIKES));
            add(new Card(TWO, HEARTS));
            add(new Card(FIVE, HEARTS));
        }};
        PokerHandFactory pcf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(FOUR, HEARTS));
            add(new Card(EIGHT, HEARTS));
        }};
        PokerHand flush = pcf.createCombination(playerCards);
        assertEquals(FLUSH, flush.getName());
        assertTrue(flush.compareTo(new Flush(asList(KING, EIGHT, FIVE, FOUR, TWO))) == 0);
    }

    @Test
    public void testFlushCreation2() {
        Cards commonCards = new Cards() {{
            add(new Card(JACK, HEARTS));
            add(new Card(ACE, TILES));
            add(new Card(TEN, PIKES));
            add(new Card(TWO, HEARTS));
            add(new Card(SEVEN, HEARTS));
        }};
        PokerHandFactory pcf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(FOUR, HEARTS));
            add(new Card(QUEEN, HEARTS));
        }};
        PokerHand flush = pcf.createCombination(playerCards);
        assertEquals(FLUSH, flush.getName());
        assertTrue(flush.compareTo(new Flush(asList(QUEEN, JACK, SEVEN, FOUR, TWO))) == 0);
    }

    @Test
    public void testStraightCreation1() {
        Cards commonCards = new Cards() {{
            add(new Card(JACK, HEARTS));
            add(new Card(ACE, TILES));
            add(new Card(TEN, PIKES));
            add(new Card(TWO, CLOVERS));
            add(new Card(KING, HEARTS));
        }};
        PokerHandFactory pcf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(FOUR, HEARTS));
            add(new Card(QUEEN, HEARTS));
        }};
        PokerHand straight = pcf.createCombination(playerCards);
        assertEquals(STRAIGHT, straight.getName());
        assertTrue(straight.compareTo(new Straight(ACE)) == 0);
    }

    @Test
    public void testStraightCreation2() {
        Cards commonCards = new Cards() {{
            add(new Card(THREE, HEARTS));
            add(new Card(ACE, TILES));
            add(new Card(FIVE, PIKES));
            add(new Card(TWO, CLOVERS));
            add(new Card(KING, HEARTS));
        }};
        PokerHandFactory pcf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(FOUR, HEARTS));
            add(new Card(QUEEN, HEARTS));
        }};
        PokerHand straight = pcf.createCombination(playerCards);
        assertEquals(STRAIGHT, straight.getName());
        assertTrue(straight.compareTo(new Straight(FIVE)) == 0);
    }

    @Test
    public void testThreeOfAKindCreation1() {
        Cards commonCards = new Cards() {{
            add(new Card(ACE, PIKES));
            add(new Card(NINE, TILES));
            add(new Card(TEN, HEARTS));
            add(new Card(TWO, TILES));
            add(new Card(ACE, CLOVERS));
        }};
        PokerHandFactory pcf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(JACK, PIKES));
            add(new Card(ACE, HEARTS));

        }};
        PokerHand threeOfAKind = pcf.createCombination(playerCards);
        assertEquals(THREE_OF_A_KIND, threeOfAKind.getName());
        List<Rank> otherCardsRanks = new ArrayList<Rank>() {{
            add(JACK);
            add(TEN);
        }};
        assertTrue(threeOfAKind.compareTo(new ThreeOfAKind(ACE, otherCardsRanks)) == 0);
    }

    @Test
    public void testThreeOfAKindCreation2() {
        Cards commonCards = new Cards() {{
            add(new Card(ACE, PIKES));
            add(new Card(JACK, TILES));
            add(new Card(TEN, HEARTS));
            add(new Card(TWO, TILES));
            add(new Card(THREE, CLOVERS));
        }};
        PokerHandFactory pcf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(JACK, PIKES));
            add(new Card(JACK, HEARTS));

        }};
        PokerHand threeOfAKind = pcf.createCombination(playerCards);
        assertEquals(THREE_OF_A_KIND, threeOfAKind.getName());
        List<Rank> otherCardsRanks = new ArrayList<Rank>() {{
            add(ACE);
            add(TEN);
        }};
        assertTrue(threeOfAKind.compareTo(new ThreeOfAKind(JACK, otherCardsRanks)) == 0);
    }

    @Test
    public void testTwoPairsCreation1() {
        Cards commonCards = new Cards() {{
            add(new Card(TEN, PIKES));
            add(new Card(JACK, TILES));
            add(new Card(EIGHT, HEARTS));
            add(new Card(TWO, TILES));
            add(new Card(THREE, CLOVERS));
        }};
        PokerHandFactory phf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(TWO, PIKES));
            add(new Card(JACK, HEARTS));
        }};
        PokerHand twoPairs = phf.createCombination(playerCards);
        assertEquals(TWO_PAIRS, twoPairs.getName());
        assertTrue(twoPairs.compareTo(new TwoPairs(JACK, TWO, TEN)) == 0);
    }

    @Test
    public void testTwoPairsCreation2() {
        Cards commonCards = new Cards() {{
            add(new Card(TEN, PIKES));
            add(new Card(KING, TILES));
            add(new Card(TWO, HEARTS));
            add(new Card(TWO, TILES));
            add(new Card(NINE, CLOVERS));
        }};
        PokerHandFactory phf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(THREE, PIKES));
            add(new Card(KING, HEARTS));
        }};
        PokerHand twoPairs = phf.createCombination(playerCards);
        assertEquals(TWO_PAIRS, twoPairs.getName());
        assertTrue(twoPairs.compareTo(new TwoPairs(KING, TWO, TEN)) == 0);
    }

    @Test
    public void testTwoPairsCreation3() {
        Cards commonCards = new Cards() {{
            add(new Card(QUEEN, CLOVERS));
            add(new Card(JACK, CLOVERS));
            add(new Card(TWO, TILES));
            add(new Card(NINE, CLOVERS));
            add(new Card(TEN, CLOVERS));
        }};
        PokerHandFactory phf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(QUEEN, PIKES));
            add(new Card(JACK, PIKES));
        }};
        PokerHand twoPairs = phf.createCombination(playerCards);
        assertEquals(TWO_PAIRS, twoPairs.getName());
        assertTrue(twoPairs.compareTo(new TwoPairs(QUEEN, JACK, TEN)) == 0);
    }

    @Test
    public void testPairCreation1() {
        Cards commonCards = new Cards() {{
            add(new Card(TEN, PIKES));
            add(new Card(NINE, TILES));
            add(new Card(EIGHT, HEARTS));
            add(new Card(TWO, TILES));
            add(new Card(ACE, CLOVERS));
        }};
        PokerHandFactory pcf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(JACK, PIKES));
            add(new Card(ACE, HEARTS));

        }};
        PokerHand pair = pcf.createCombination(playerCards);
        assertEquals(PAIR, pair.getName());
        List<Rank> otherCardsRanks = new ArrayList<Rank>() {{
            add(JACK);
            add(TEN);
            add(NINE);
        }};
        assertTrue(pair.compareTo(new Pair(ACE, otherCardsRanks)) == 0);
    }

    @Test
    public void testPairCreation2() {
        Cards commonCards = new Cards() {{
            add(new Card(TEN, PIKES));
            add(new Card(FOUR, TILES));
            add(new Card(EIGHT, HEARTS));
            add(new Card(TWO, TILES));
            add(new Card(THREE, CLOVERS));
        }};
        PokerHandFactory pcf = new PokerHandFactory(commonCards);
        Cards playerCards = new Cards() {{
            add(new Card(JACK, PIKES));
            add(new Card(JACK, HEARTS));
        }};
        PokerHand pair = pcf.createCombination(playerCards);
        assertEquals(PAIR, pair.getName());
        List<Rank> otherCardsRanks = new ArrayList<Rank>() {{
            add(TEN);
            add(EIGHT);
            add(FOUR);
        }};
        assertTrue(pair.compareTo(new Pair(JACK, otherCardsRanks)) == 0);
    }

    @Test
    public void testHighCardCreation1() {
        Cards commonCards = new Cards() {{
            add(new Card(TEN, PIKES));
            add(new Card(FOUR, TILES));
            add(new Card(EIGHT, HEARTS));
            add(new Card(TWO, TILES));
            add(new Card(SIX, CLOVERS));
        }};
        PokerHandFactory pcf = new PokerHandFactory(commonCards);
        Cards playerCards1 = new Cards() {{
            add(new Card(KING, PIKES));
            add(new Card(JACK, HEARTS));
        }};
        PokerHand highCard1 = pcf.createCombination(playerCards1);
        assertEquals(HIGH_CARD, highCard1.getName());
    }

}