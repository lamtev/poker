package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Rank;
import com.lamtev.poker.core.model.Suit;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.lamtev.poker.core.combinations.Straight.isStraight;
import static org.junit.Assert.*;

public class StraightTest {

    @Test
    public void testIsStraight1() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.ACE, Suit.CLOVERS));
        cards.add(new Card(Rank.KING, Suit.CLOVERS));
        cards.add(new Card(Rank.QUEEN, Suit.PIKES));
        cards.add(new Card(Rank.TWO, Suit.CLOVERS));
        cards.add(new Card(Rank.FOUR, Suit.CLOVERS));
        cards.add(new Card(Rank.JACK, Suit.HEARTS));
        cards.add(new Card(Rank.TEN, Suit.CLOVERS));
        assertTrue(isStraight(cards));
    }

    @Test
    public void testIsStraight2() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.ACE, Suit.CLOVERS));
        cards.add(new Card(Rank.KING, Suit.CLOVERS));
        cards.add(new Card(Rank.FIVE, Suit.CLOVERS));
        cards.add(new Card(Rank.THREE, Suit.HEARTS));
        cards.add(new Card(Rank.FOUR, Suit.CLOVERS));
        cards.add(new Card(Rank.TWO, Suit.TILES));
        assertTrue(isStraight(cards));
    }

}