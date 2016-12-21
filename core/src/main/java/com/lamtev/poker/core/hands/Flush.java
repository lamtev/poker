package com.lamtev.poker.core.hands;

import com.lamtev.poker.core.model.Rank;

import static com.lamtev.poker.core.hands.PokerHand.Name.FLUSH;

public class Flush implements PokerHand {

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
    public int compareTo(PokerHand o) {
        int cmp = NAME.compareTo(o.getName());
        if (cmp == 0) {
            Flush flush = (Flush) o;
            return highCardRank.compareTo(flush.highCardRank);
        } else {
            return cmp;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flush)) return false;

        Flush flush = (Flush) o;

        return NAME == flush.NAME && highCardRank == flush.highCardRank;
    }

    @Override
    public int hashCode() {
        int result = NAME.hashCode();
        result = 31 * result + (highCardRank != null ? highCardRank.hashCode() : 0);
        return result;
    }
}
