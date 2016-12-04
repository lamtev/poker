package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Cards;

import static com.lamtev.poker.core.combinations.Flush.isFlush;
import static com.lamtev.poker.core.combinations.PokerCombination.Name.ROYAL_FLUSH;

public class RoyalFlush implements PokerCombination {

    private final Name NAME = ROYAL_FLUSH;

    public static boolean isRoyalFlush(Cards cards) {
        return isFlush(cards);
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
