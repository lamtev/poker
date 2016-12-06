package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Rank;

import java.util.List;

import static com.lamtev.poker.core.combinations.PokerCombination.Name.FOUR_OF_A_KIND;

public class FourOfAKind implements PokerCombination {

    private final Name NAME = FOUR_OF_A_KIND;
    private Rank highCardRank;
    private Rank kicker;

    public static boolean isFourOfAKind(List<Card> cards) {
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
