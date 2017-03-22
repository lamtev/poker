package com.lamtev.poker.core.hands;

import com.lamtev.poker.core.model.Rank;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.lamtev.poker.core.model.Rank.*;
import static org.junit.Assert.assertTrue;

public class PokerHandTest {

    @Test
    public void testComparisonBetweenTwoFlushes() {
        assertTrue(new Flush(ACE).compareTo(new Flush(ACE)) == 0);
        assertTrue(new Flush(ACE).compareTo(new Flush(KING)) > 0);
        assertTrue(new Flush(KING).compareTo(new Flush(ACE)) < 0);
    }

    @Test
    public void testComparisonBetweenTwoStraights() {
        assertTrue(new Straight(ACE).compareTo(new Straight(ACE)) == 0);
        assertTrue(new Straight(ACE).compareTo(new Straight(KING)) > 0);
        assertTrue(new Straight(KING).compareTo(new Straight(ACE)) < 0);
    }

    @Test
    public void testComparisonBetweenStraightAndFlush() {
        assertTrue(new Flush(KING).compareTo(new Straight(ACE)) > 0);
        assertTrue(new Straight(ACE).compareTo(new Flush(KING)) < 0);
    }

    @Test
    public void testComparisonBetweenTwoStraightFlushes() {
        assertTrue(new StraightFlush(EIGHT).compareTo(new StraightFlush(EIGHT)) == 0);
        assertTrue(new StraightFlush(JACK).compareTo(new StraightFlush(EIGHT)) > 0);
        assertTrue(new StraightFlush(EIGHT).compareTo(new StraightFlush(JACK)) < 0);
    }

    @Test
    public void testComparisonBetweenStraightFlushAndFlush() {
        assertTrue(new StraightFlush(TWO).compareTo(new Flush(ACE)) > 0);
        assertTrue(new Flush(ACE).compareTo(new StraightFlush(TWO)) < 0);
    }

    @Test
    public void testComparisonBetweenTwoFullHouses() {
        assertTrue(new FullHouse(ACE, KING).compareTo(new FullHouse(ACE, KING)) == 0);
        assertTrue(new FullHouse(ACE, KING).compareTo(new FullHouse(KING, ACE)) > 0);
        assertTrue(new FullHouse(KING, ACE).compareTo(new FullHouse(ACE, KING)) < 0);
    }

    @Test
    public void testComparisonBetweenTwoThreeOfAKinds() {
        List<Rank> ranks = new ArrayList<Rank>() {{
           add(TEN);
           add(SEVEN);
           add(TWO);
        }};
        assertTrue(new ThreeOfAKind(QUEEN, ranks.subList(0, 2))
                .compareTo(new ThreeOfAKind(QUEEN, ranks.subList(1, 3))) > 0);
    }

    @Test
    public void testComparisonBetweenTwoTwoPairs() {
        assertTrue(new TwoPairs(ACE, SEVEN, FOUR).compareTo(new TwoPairs(ACE, SEVEN, THREE)) > 0);
    }

    @Test
    public void testComparisonBetweenTwoPairs() {
        List<Rank> ranks = new ArrayList<Rank>() {{
            add(ACE);
            add(JACK);
            add(QUEEN);
            add(TEN);
        }};
        assertTrue(new Pair(FIVE, ranks.subList(0, 3)).compareTo(new Pair(FIVE, ranks.subList(1, 4))) > 0);
    }

    @Test
    public void testComparisonBetweenTwoHighCards() {
        List<Rank> ranks = new ArrayList<Rank>() {{
            add(ACE);
            add(TEN);
            add(FOUR);
            add(EIGHT);
            add(TWO);
            add(SIX);
            add(JACK);
        }};
        assertTrue(new HighCard(ranks.subList(0, 5)).compareTo(new HighCard(ranks.subList(1, 6))) > 0);
    }

}
