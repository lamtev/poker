package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Rank;

import static com.lamtev.poker.core.combinations.PokerCombination.Name.FLUSH;

public class Flush implements PokerCombination {

    private final Name NAME = FLUSH;
    private final Rank highCardRank;

    Flush(Rank highCardRank) {
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
            Flush flush = (Flush) o;
            return highCardRank.compareTo(flush.highCardRank);
        } else {
            return cmp;
        }
    }
}
