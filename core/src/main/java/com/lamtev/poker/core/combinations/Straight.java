package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Rank;

import static com.lamtev.poker.core.combinations.PokerCombination.Name.STRAIGHT;

public class Straight implements PokerCombination {

    private final Name NAME = STRAIGHT;
    private final Rank highCardRank;

    public Straight(Rank highCardRank) {
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
            Straight straight = (Straight) o;
            return highCardRank.compareTo(straight.highCardRank);
        } else {
            return cmp;
        }
    }
}
