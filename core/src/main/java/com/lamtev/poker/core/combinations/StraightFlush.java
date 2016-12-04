package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Cards;

import static com.lamtev.poker.core.combinations.PokerCombination.Name.STRAIGHT_FLUSH;
import static com.lamtev.poker.core.combinations.Straight.isStraight;
import static com.lamtev.poker.core.combinations.Flush.isFlush;

public class StraightFlush implements PokerCombination {

    private final Name NAME = STRAIGHT_FLUSH;

    public static boolean isStraightFlush(Cards cards) {
        return isStraight(cards) && isFlush(cards);
    }

    @Override
    public Name getName() {
        return NAME;
    }

    @Override
    public int compareTo(PokerCombination o) {
        return 0;
    }
}
