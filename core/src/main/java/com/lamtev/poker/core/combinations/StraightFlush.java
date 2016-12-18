package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Rank;

import static com.lamtev.poker.core.combinations.PokerCombination.Name.STRAIGHT_FLUSH;

public class StraightFlush implements PokerCombination {

    private final Name NAME = STRAIGHT_FLUSH;
    private final Rank highCardRank;

    public StraightFlush(Rank highCardRank) {
        this.highCardRank = highCardRank;
    }

    @Override
    public Name getName() {
        return NAME;
    }

    @Override
    public int compareTo(PokerCombination o) {
        int cmp = NAME.compareTo(o.getName());
        if (cmp == 0) {
            return highCardRank.compareTo(((StraightFlush) o).highCardRank);
        } else {
            return cmp;
        }
    }
}
