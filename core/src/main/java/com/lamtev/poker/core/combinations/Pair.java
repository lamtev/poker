package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Rank;

import static com.lamtev.poker.core.combinations.PokerCombination.Name.PAIR;

public class Pair implements PokerCombination {

    private final Name NAME = PAIR;
    private final Rank highCardRank;
    private final Rank kicker;

    public Pair(Rank highCardRank, Rank kicker) {
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
