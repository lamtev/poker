package com.lamtev.poker.core.hands;

import com.lamtev.poker.core.model.Rank;

import static com.lamtev.poker.core.hands.PokerHand.Name.STRAIGHT;

class Straight implements PokerHand {

    private final Name NAME = STRAIGHT;
    private final Rank highCardRank;

    Straight(Rank highCardRank) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Straight)) return false;

        Straight straight = (Straight) o;

        if (NAME != straight.NAME) return false;
        return highCardRank == straight.highCardRank;
    }

    @Override
    public int hashCode() {
        int result = NAME.hashCode();
        result = 31 * result + (highCardRank != null ? highCardRank.hashCode() : 0);
        return result;
    }
}
