package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Rank;
import com.lamtev.poker.core.model.Suit;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.lamtev.poker.core.combinations.Flush.isFlush;
import static org.junit.Assert.assertTrue;

public class FlushTest {

    @Test
    public void testIsFlush() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.THREE, Suit.CLOVERS));
        cards.add(new Card(Rank.KING, Suit.CLOVERS));
        cards.add(new Card(Rank.FIVE, Suit.PIKES));
        cards.add(new Card(Rank.TWO, Suit.CLOVERS));
        cards.add(new Card(Rank.FOUR, Suit.CLOVERS));
        cards.add(new Card(Rank.JACK, Suit.HEARTS));
        cards.add(new Card(Rank.TEN, Suit.CLOVERS));
        assertTrue(isFlush(cards));
    }

}