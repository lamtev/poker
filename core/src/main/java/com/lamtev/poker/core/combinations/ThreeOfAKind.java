package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Rank;

import java.util.List;

import static com.lamtev.poker.core.combinations.PokerCombination.Name.THREE_OF_A_KIND;

public class ThreeOfAKind implements PokerCombination {

    private final Name NAME = THREE_OF_A_KIND;
    private Rank highCardRank;
    private Rank kicker;

    public static boolean isThreeOfAKind(List<Card> cards) {
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
