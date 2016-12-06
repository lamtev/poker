package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Rank;
import com.lamtev.poker.core.model.Suit;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.lamtev.poker.core.combinations.RoyalFlush.isRoyalFlush;
import static org.junit.Assert.assertTrue;

public class RoyalFlushTest {

    @Test
    public void testIsRoyalFlush() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.ACE, Suit.CLOVERS));
        cards.add(new Card(Rank.KING, Suit.CLOVERS));
        cards.add(new Card(Rank.QUEEN, Suit.CLOVERS));
        cards.add(new Card(Rank.JACK, Suit.CLOVERS));
        cards.add(new Card(Rank.ACE, Suit.PIKES));
        cards.add(new Card(Rank.TEN, Suit.CLOVERS));
        cards.add(new Card(Rank.TWO, Suit.HEARTS));
        assertTrue(isRoyalFlush(cards));
    }

}