package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Rank;

import java.util.List;

import static com.lamtev.poker.core.combinations.PokerCombination.Name.FULL_HOUSE;

public class FullHouse implements PokerCombination {

    private final Name NAME = FULL_HOUSE;
    private Rank ThreeOfAKindHighCardRank;
    private Rank PairHighCardRank;

    public static boolean isFullHouse(List<Card> cards) {
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
