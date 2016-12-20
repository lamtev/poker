package com.lamtev.poker.core.hands;

import com.lamtev.poker.core.model.Rank;

import static com.lamtev.poker.core.hands.PokerHand.Name.STRAIGHT;

public class Straight implements PokerHand {

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
    public int compareTo(PokerHand o) {
        int cmp = NAME.compareTo(o.getName());
        if (cmp == 0) {
            Straight straight = (Straight) o;
            return highCardRank.compareTo(straight.highCardRank);
        } else {
            return cmp;
        }
    }
}
