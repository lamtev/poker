package com.lamtev.poker.core.hands;

import com.lamtev.poker.core.model.Rank;

import static com.lamtev.poker.core.hands.PokerHand.Name.TWO_PAIRS;

class TwoPairs implements PokerHand {

    private final Name NAME = TWO_PAIRS;
    private final Rank firstPairRank;
    private final Rank secondPairRank;
    private final Rank otherCardRank;

    TwoPairs(Rank firstPairRank, Rank secondPairRank, Rank otherCardRank) {
        this.firstPairRank = firstPairRank;
        this.secondPairRank = secondPairRank;
        this.otherCardRank = otherCardRank;
    }

    @Override
    public Name getName() {
        return NAME;
    }

    @Override
    public int compareTo(PokerHand o) {
        int cmp1 = NAME.compareTo(o.getName());
        if (cmp1 == 0) {
            TwoPairs twoPairs = (TwoPairs) o;
            int cmp2 = firstPairRank.compareTo(twoPairs.firstPairRank);
            if (cmp2 == 0) {
                int cmp3 = secondPairRank.compareTo(twoPairs.secondPairRank);
                if (cmp3 == 0) {
                    return otherCardRank.compareTo(twoPairs.otherCardRank);
                } else {
                    return cmp3;
                }
            } else {
                return cmp2;
            }
        } else {
            return cmp1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TwoPairs)) return false;

        TwoPairs twoPairs = (TwoPairs) o;

        if (NAME != twoPairs.NAME) return false;
        if (firstPairRank != twoPairs.firstPairRank) return false;
        if (secondPairRank != twoPairs.secondPairRank) return false;
        return otherCardRank == twoPairs.otherCardRank;
    }

    @Override
    public int hashCode() {
        int result = NAME.hashCode();
        result = 31 * result + (firstPairRank != null ? firstPairRank.hashCode() : 0);
        result = 31 * result + (secondPairRank != null ? secondPairRank.hashCode() : 0);
        result = 31 * result + (otherCardRank != null ? otherCardRank.hashCode() : 0);
        return result;
    }
}
