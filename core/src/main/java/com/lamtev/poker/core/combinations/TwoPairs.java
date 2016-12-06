package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Rank;

import java.util.List;

import static com.lamtev.poker.core.combinations.PokerCombination.Name.TWO_PAIRS;

public class TwoPairs implements PokerCombination {

    private final Name NAME = TWO_PAIRS;
    private Rank firstPairHighCardRank;
    private Rank secondPairHihCardRank;
    private Rank kicker;

    public static boolean isTwoPairs(List<Card> cards) {
        //TODO implement
        return false;
    }

    @Override
    public Name getName() {
        return NAME;
    }

    @Override
    public int compareTo(PokerCombination o) {
        //TODO implement
        return 0;
    }
}
