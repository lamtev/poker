package com.lamtev.poker.core.hands;

import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.model.Rank;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.lamtev.poker.core.hands.PokerHand.Name.*;
import static com.lamtev.poker.core.hands.PokerHandFactory.createCombination;
import static com.lamtev.poker.core.model.Rank.*;
import static com.lamtev.poker.core.model.Suit.*;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PokerHandFactoryTest {

    @Test
    public void testRoyalFlushCreation1() {
        Cards communityCards = Cards.of(asList(
                new Card(TWO, HEARTS),
                new Card(NINE, HEARTS),
                new Card(TEN, HEARTS),
                new Card(JACK, HEARTS),
                new Card(QUEEN, HEARTS)
        ));
        
        Cards playerCards = Cards.of(asList(new Card(KING, HEARTS), new Card(ACE, HEARTS)));

        PokerHand royalFlush = createCombination(playerCards, communityCards);
        assertEquals(ROYAL_FLUSH, royalFlush.getName());
        assertTrue(royalFlush.compareTo(new RoyalFlush()) == 0);
    }

    @Test
    public void testStraightFlushCreation1() {
        Cards communityCards = Cards.of(asList(
                new Card(THREE, PIKES),
                new Card(ACE, PIKES),
                new Card(FIVE, PIKES),
                new Card(TWO, PIKES),
                new Card(KING, HEARTS)
        ));

        Cards playerCards = Cards.of(asList(new Card(FOUR, PIKES), new Card(QUEEN, HEARTS)));

        PokerHand straightFlush = createCombination(playerCards, communityCards);
        assertEquals(STRAIGHT_FLUSH, straightFlush.getName());
        assertTrue(straightFlush.compareTo(new StraightFlush(FIVE)) == 0);
    }

    @Test
    public void testStraightFlushCreation2() {
        Cards communityCards = Cards.of(asList(
                new Card(TEN, PIKES),
                new Card(ACE, TILES),
                new Card(JACK, PIKES),
                new Card(TWO, CLOVERS),
                new Card(KING, PIKES)
        ));

        Cards playerCards = Cards.of(asList(new Card(NINE, PIKES), new Card(QUEEN, PIKES)));

        PokerHand straightFlush = createCombination(playerCards, communityCards);
        assertEquals(STRAIGHT_FLUSH, straightFlush.getName());
        assertTrue(straightFlush.compareTo(new StraightFlush(KING)) == 0);
    }

    @Test
    public void testStraightFlushCreation3() {
        Cards communityCards = Cards.of(asList(
                new Card(TWO, PIKES),
                new Card(THREE, PIKES),
                new Card(FOUR, PIKES),
                new Card(FIVE, PIKES),
                new Card(SIX, PIKES)
        ));

        Cards playerCards = Cards.of(asList(new Card(SEVEN, PIKES), new Card(EIGHT, PIKES)));

        PokerHand straightFlush = createCombination(playerCards, communityCards);
        assertEquals(STRAIGHT_FLUSH, straightFlush.getName());
        assertTrue(straightFlush.compareTo(new StraightFlush(EIGHT)) == 0);
    }

    @Test
    public void testFourOfAKindCreation1() {
        Cards communityCards = Cards.of(asList(
                new Card(TEN, PIKES),
                new Card(KING, TILES),
                new Card(TWO, HEARTS),
                new Card(TWO, TILES),
                new Card(TWO, CLOVERS)
        ));

        Cards playerCards = Cards.of(asList(new Card(TWO, PIKES), new Card(KING, HEARTS)));

        PokerHand fourOfAKind = createCombination(playerCards, communityCards);
        assertEquals(FOUR_OF_A_KIND, fourOfAKind.getName());
        assertTrue(fourOfAKind.compareTo(new FourOfAKind(TWO, KING)) == 0);
    }

    @Test
    public void testFourOfAKindCreation2() {
        Cards communityCards = Cards.of(
                asList(new Card(FOUR, PIKES),
                        new Card(ACE, TILES),
                        new Card(FOUR, HEARTS),
                        new Card(FOUR, TILES),
                        new Card(FOUR, CLOVERS)
                ));

        Cards playerCards = Cards.of(asList(new Card(TWO, PIKES), new Card(KING, HEARTS)));

        PokerHand fourOfAKind = createCombination(playerCards, communityCards);
        assertEquals(FOUR_OF_A_KIND, fourOfAKind.getName());
        assertTrue(fourOfAKind.compareTo(new FourOfAKind(FOUR, ACE)) == 0);
    }

    @Test
    public void testFullHouseCreation1() {
        Cards communityCards = Cards.of(asList(
                new Card(FOUR, PIKES),
                new Card(KING, TILES),
                new Card(FOUR, HEARTS),
                new Card(FOUR, TILES),
                new Card(TWO, CLOVERS)
        ));

        Cards playerCards = Cards.of(asList(new Card(TWO, PIKES), new Card(KING, HEARTS)));

        PokerHand fullHouse = createCombination(playerCards, communityCards);
        assertEquals(FULL_HOUSE, fullHouse.getName());
        assertTrue(fullHouse.compareTo(new FullHouse(FOUR, KING)) == 0);
    }

    @Test
    public void testFullHouseCreation2() {
        Cards communityCards = Cards.of(asList(
                new Card(TWO, PIKES),
                new Card(FIVE, TILES),
                new Card(FIVE, HEARTS),
                new Card(FOUR, TILES),
                new Card(THREE, CLOVERS)
        ));

        Cards playerCards = Cards.of(asList(new Card(FOUR, PIKES), new Card(FOUR, CLOVERS)));

        PokerHand fullHouse = createCombination(playerCards, communityCards);
        assertEquals(FULL_HOUSE, fullHouse.getName());
        assertTrue(fullHouse.compareTo(new FullHouse(FOUR, FIVE)) == 0);
    }

    @Test
    public void testFlushCreation1() {
        Cards communityCards = Cards.of(asList(
                new Card(ACE, TILES),
                new Card(KING, HEARTS),
                new Card(TEN, PIKES),
                new Card(TWO, HEARTS),
                new Card(FIVE, HEARTS)
        ));

        Cards playerCards = Cards.of(asList(new Card(FOUR, HEARTS), new Card(EIGHT, HEARTS)));

        PokerHand flush = createCombination(playerCards, communityCards);
        assertEquals(FLUSH, flush.getName());
        assertTrue(flush.compareTo(new Flush(asList(KING, EIGHT, FIVE, FOUR, TWO))) == 0);
    }

    @Test
    public void testFlushCreation2() {
        Cards communityCards = Cards.of(asList(
                new Card(JACK, HEARTS),
                new Card(ACE, TILES),
                new Card(TEN, PIKES),
                new Card(TWO, HEARTS),
                new Card(SEVEN, HEARTS)
        ));

        
        Cards playerCards = Cards.of(asList(new Card(FOUR, HEARTS), new Card(QUEEN, HEARTS)));

        PokerHand flush = createCombination(playerCards, communityCards);
        assertEquals(FLUSH, flush.getName());
        assertTrue(flush.compareTo(new Flush(asList(QUEEN, JACK, SEVEN, FOUR, TWO))) == 0);
    }

    @Test
    public void testStraightCreation1() {
        Cards communityCards = Cards.of(asList(
                new Card(JACK, HEARTS),
                new Card(ACE, TILES),
                new Card(TEN, PIKES),
                new Card(TWO, CLOVERS),
                new Card(KING, HEARTS)
        ));

        Cards playerCards = Cards.of(asList(new Card(FOUR, HEARTS), new Card(QUEEN, HEARTS)));

        PokerHand straight = createCombination(playerCards, communityCards);
        assertEquals(STRAIGHT, straight.getName());
        assertTrue(straight.compareTo(new Straight(ACE)) == 0);
    }

    @Test
    public void testStraightCreation2() {
        Cards communityCards = Cards.of(asList(
                new Card(THREE, HEARTS),
                new Card(ACE, TILES),
                new Card(FIVE, PIKES),
                new Card(TWO, CLOVERS),
                new Card(KING, HEARTS)
        ));

        Cards playerCards = Cards.of(asList(new Card(FOUR, HEARTS), new Card(QUEEN, HEARTS)));

        PokerHand straight = createCombination(playerCards, communityCards);
        assertEquals(STRAIGHT, straight.getName());
        assertTrue(straight.compareTo(new Straight(FIVE)) == 0);
    }

    @Test
    public void testThreeOfAKindCreation1() {
        Cards communityCards = Cards.of(asList(
                new Card(ACE, PIKES),
                new Card(NINE, TILES),
                new Card(TEN, HEARTS),
                new Card(TWO, TILES),
                new Card(ACE, CLOVERS)
        ));

        Cards playerCards = Cards.of(asList(new Card(JACK, PIKES), new Card(ACE, HEARTS)));

        PokerHand threeOfAKind = createCombination(playerCards, communityCards);
        assertEquals(THREE_OF_A_KIND, threeOfAKind.getName());
        List<Rank> otherCardsRanks = new ArrayList<Rank>() {{
            add(JACK);
            add(TEN);
        }};
        assertTrue(threeOfAKind.compareTo(new ThreeOfAKind(ACE, otherCardsRanks)) == 0);
    }

    @Test
    public void testThreeOfAKindCreation2() {
        Cards communityCards = Cards.of(asList(
                new Card(ACE, PIKES),
                new Card(JACK, TILES),
                new Card(TEN, HEARTS),
                new Card(TWO, TILES),
                new Card(THREE, CLOVERS)
        ));

        
        Cards playerCards = Cards.of(asList(new Card(JACK, PIKES), new Card(JACK, HEARTS)));

        PokerHand threeOfAKind = createCombination(playerCards, communityCards);
        assertEquals(THREE_OF_A_KIND, threeOfAKind.getName());
        List<Rank> otherCardsRanks = new ArrayList<Rank>() {{
            add(ACE);
            add(TEN);
        }};
        assertTrue(threeOfAKind.compareTo(new ThreeOfAKind(JACK, otherCardsRanks)) == 0);
    }

    @Test
    public void testTwoPairsCreation1() {
        Cards communityCards = Cards.of(asList(
                new Card(TEN, PIKES),
                new Card(JACK, TILES),
                new Card(EIGHT, HEARTS),
                new Card(TWO, TILES),
                new Card(THREE, CLOVERS)
        ));

        Cards playerCards = Cards.of(asList(new Card(TWO, PIKES), new Card(JACK, HEARTS)));

        PokerHand twoPairs = createCombination(playerCards, communityCards);
        assertEquals(TWO_PAIRS, twoPairs.getName());
        assertTrue(twoPairs.compareTo(new TwoPairs(JACK, TWO, TEN)) == 0);
    }

    @Test
    public void testTwoPairsCreation2() {
        Cards communityCards = Cards.of(asList(
                new Card(TEN, PIKES),
                new Card(KING, TILES),
                new Card(TWO, HEARTS),
                new Card(TWO, TILES),
                new Card(NINE, CLOVERS)
        ));

        Cards playerCards = Cards.of(asList(new Card(THREE, PIKES), new Card(KING, HEARTS)));

        PokerHand twoPairs = createCombination(playerCards, communityCards);
        assertEquals(TWO_PAIRS, twoPairs.getName());
        assertTrue(twoPairs.compareTo(new TwoPairs(KING, TWO, TEN)) == 0);
    }

    @Test
    public void testTwoPairsCreation3() {
        Cards communityCards = Cards.of(asList(
                new Card(QUEEN, CLOVERS),
                new Card(JACK, CLOVERS),
                new Card(TWO, TILES),
                new Card(NINE, CLOVERS),
                new Card(TEN, CLOVERS)
        ));

        
        Cards playerCards = Cards.of(asList(new Card(QUEEN, PIKES), new Card(JACK, PIKES)));

        PokerHand twoPairs = createCombination(playerCards, communityCards);
        assertEquals(TWO_PAIRS, twoPairs.getName());
        assertTrue(twoPairs.compareTo(new TwoPairs(QUEEN, JACK, TEN)) == 0);
    }

    @Test
    public void testPairCreation1() {
        Cards communityCards = Cards.of(asList(
                new Card(TEN, PIKES),
                new Card(NINE, TILES),
                new Card(EIGHT, HEARTS),
                new Card(TWO, TILES),
                new Card(ACE, CLOVERS)
        ));

        
        Cards playerCards = Cards.of(asList(new Card(JACK, PIKES), new Card(ACE, HEARTS)));

        PokerHand pair = createCombination(playerCards, communityCards);
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
        Cards communityCards = Cards.of(asList(
                new Card(TEN, PIKES),
                new Card(FOUR, TILES),
                new Card(EIGHT, HEARTS),
                new Card(TWO, TILES),
                new Card(THREE, CLOVERS)
        ));

        Cards playerCards = Cards.of(asList(new Card(JACK, PIKES), new Card(JACK, HEARTS)));

        PokerHand pair = createCombination(playerCards, communityCards);
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
        Cards communityCards = Cards.of(asList(
                new Card(TEN, PIKES),
                new Card(FOUR, TILES),
                new Card(EIGHT, HEARTS),
                new Card(TWO, TILES),
                new Card(SIX, CLOVERS)
        ));

        Cards playerCards = Cards.of(asList(new Card(KING, PIKES), new Card(JACK, HEARTS)));

        PokerHand highCard1 = createCombination(playerCards, communityCards);
        assertEquals(HIGH_CARD, highCard1.getName());
    }

}