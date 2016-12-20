package com.lamtev.poker.core.hands;

import com.lamtev.poker.core.model.Rank;

import static com.lamtev.poker.core.hands.PokerHand.Name.FOUR_OF_A_KIND;

public class FourOfAKind implements PokerHand {

    private final Name NAME = FOUR_OF_A_KIND;
    private final Rank highCardRank;
    private final Rank otherCardRank;

    public FourOfAKind(Rank highCardRank, Rank otherCardRank) {
        this.highCardRank = highCardRank;
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
            FourOfAKind fourOfAKind = (FourOfAKind) o;
            int cmp2 = highCardRank.compareTo(fourOfAKind.highCardRank);
            if (cmp2 == 0) {
                return otherCardRank.compareTo(fourOfAKind.otherCardRank);
            } else {
                return cmp2;
            }
        } else {
            return cmp1;
        }
    }
}
