package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Rank;

import static com.lamtev.poker.core.combinations.PokerCombination.Name.TWO_PAIRS;

public class TwoPairs implements PokerCombination {

    private final Name NAME = TWO_PAIRS;
    private final Rank firstPairHighCardRank;
    private final Rank secondPairHihCardRank;
    private final Rank kicker;

    public TwoPairs(Rank firstPairHighCardRank, Rank secondPairHihCardRank, Rank kicker) {
        this.firstPairHighCardRank = firstPairHighCardRank;
        this.secondPairHihCardRank = secondPairHihCardRank;
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
