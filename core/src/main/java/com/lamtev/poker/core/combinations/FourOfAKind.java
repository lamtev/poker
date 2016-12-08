package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Rank;

import static com.lamtev.poker.core.combinations.PokerCombination.Name.FOUR_OF_A_KIND;

public class FourOfAKind implements PokerCombination {

    private final Name NAME = FOUR_OF_A_KIND;
    private final Rank highCardRank;
    private final Rank kicker;

    public FourOfAKind(Rank highCardRank, Rank kicker) {
        this.highCardRank = highCardRank;
        this.kicker = kicker;
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
