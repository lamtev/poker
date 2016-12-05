package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Card;

import java.util.List;

import static com.lamtev.poker.core.combinations.PokerCombination.Name.ROYAL_FLUSH;
import static com.lamtev.poker.core.combinations.StraightFlush.isStraightFlush;

public class RoyalFlush implements PokerCombination {

    private final Name NAME = ROYAL_FLUSH;

    public static boolean isRoyalFlush(List<Card> cards) {
        return isStraightFlush(cards);
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
