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
}
