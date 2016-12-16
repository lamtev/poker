package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Rank;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PokerCombinationTest {

    @Test
    public void testComparisonBetweenFlushAndFlush() {
        assertTrue(new Flush(Rank.ACE).compareTo(new Flush(Rank.ACE)) == 0);
        assertTrue(new Flush(Rank.ACE).compareTo(new Flush(Rank.KING)) > 0);
    }

    @Test
    public void testComparisonBetweenStraightAndStraight() {
        assertTrue(new Straight(Rank.ACE).compareTo(new Straight(Rank.ACE)) == 0);
        assertTrue(new Straight(Rank.ACE).compareTo(new Straight(Rank.KING)) > 0);
    }

    @Test
    public void testComparisonBetweenStraightAndFlush() {
        assertTrue(new Straight(Rank.ACE).compareTo(new Flush(Rank.KING)) < 0);
    }

    //TODO add more test cases

}
