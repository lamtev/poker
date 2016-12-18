package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Rank;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PokerCombinationTest {

    @Test
    public void testComparisonBetweenFlushAndFlush() {
        assertTrue(new Flush(Rank.ACE).compareTo(new Flush(Rank.ACE)) == 0);
        assertTrue(new Flush(Rank.ACE).compareTo(new Flush(Rank.KING)) > 0);
        assertTrue(new Flush(Rank.KING).compareTo(new Flush(Rank.ACE)) < 0);
    }

    @Test
    public void testComparisonBetweenStraightAndStraight() {
        assertTrue(new Straight(Rank.ACE).compareTo(new Straight(Rank.ACE)) == 0);
        assertTrue(new Straight(Rank.ACE).compareTo(new Straight(Rank.KING)) > 0);
        assertTrue(new Straight(Rank.KING).compareTo(new Straight(Rank.ACE)) < 0);
    }

    @Test
    public void testComparisonBetweenStraightAndFlush() {
        assertTrue(new Flush(Rank.KING).compareTo(new Straight(Rank.ACE)) > 0);
        assertTrue(new Straight(Rank.ACE).compareTo(new Flush(Rank.KING)) < 0);
    }

    @Test
    public void testComparisonBetweenStraightFlushAndStraightFlush() {
        assertTrue(new StraightFlush(Rank.EIGHT).compareTo(new StraightFlush(Rank.EIGHT)) == 0);
        assertTrue(new StraightFlush(Rank.JACK).compareTo(new StraightFlush(Rank.EIGHT)) > 0);
        assertTrue(new StraightFlush(Rank.EIGHT).compareTo(new StraightFlush(Rank.JACK)) < 0);
    }

    @Test
    public void testComparisonBetweenStraightFlushAndFlush() {
        assertTrue(new StraightFlush(Rank.TWO).compareTo(new Flush(Rank.ACE)) > 0);
        assertTrue(new Flush(Rank.ACE).compareTo(new StraightFlush(Rank.TWO)) < 0);
    }

    @Test
    public void testComparisonBetweenFullHouseAndFullHouse() {
        assertTrue(new FullHouse(Rank.ACE, Rank.KING).compareTo(new FullHouse(Rank.ACE, Rank.KING)) == 0);
        assertTrue(new FullHouse(Rank.ACE, Rank.KING).compareTo(new FullHouse(Rank.KING, Rank.ACE)) > 0);
        assertTrue(new FullHouse(Rank.KING, Rank.ACE).compareTo(new FullHouse(Rank.ACE, Rank.KING)) < 0);
    }

    //TODO add more test cases

}
