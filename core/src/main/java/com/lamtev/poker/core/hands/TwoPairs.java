package com.lamtev.poker.core.hands;

import com.lamtev.poker.core.model.Rank;

import static com.lamtev.poker.core.hands.PokerHand.Name.TWO_PAIRS;

public class TwoPairs implements PokerHand {

    private final Name NAME = TWO_PAIRS;
    private final Rank firstPairHighCardRank;
    private final Rank secondPairHihCardRank;
    private final Rank otherCardRank;

    public TwoPairs(Rank firstPairHighCardRank, Rank secondPairHihCardRank, Rank otherCardRank) {
        this.firstPairHighCardRank = firstPairHighCardRank;
        this.secondPairHihCardRank = secondPairHihCardRank;
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
            int cmp2 = firstPairHighCardRank.compareTo(twoPairs.firstPairHighCardRank);
            if (cmp2 == 0) {
                int cmp3 = secondPairHihCardRank.compareTo(twoPairs.secondPairHihCardRank);
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
        if (firstPairHighCardRank != twoPairs.firstPairHighCardRank) return false;
        if (secondPairHihCardRank != twoPairs.secondPairHihCardRank) return false;
        return otherCardRank == twoPairs.otherCardRank;
    }

    @Override
    public int hashCode() {
        int result = NAME.hashCode();
        result = 31 * result + (firstPairHighCardRank != null ? firstPairHighCardRank.hashCode() : 0);
        result = 31 * result + (secondPairHihCardRank != null ? secondPairHihCardRank.hashCode() : 0);
        result = 31 * result + (otherCardRank != null ? otherCardRank.hashCode() : 0);
        return result;
    }
}
