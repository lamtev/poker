package com.lamtev.poker.core.hands;

import com.lamtev.poker.core.model.Rank;

import static com.lamtev.poker.core.hands.PokerHand.Name.FULL_HOUSE;

public class FullHouse implements PokerHand {

    private final Name NAME = FULL_HOUSE;
    private final Rank threeOfAKindHighCardRank;
    private final Rank pairHighCardRank;

    public FullHouse(Rank threeOfAKindHighCardRank, Rank pairHighCardRank) {
        this.threeOfAKindHighCardRank = threeOfAKindHighCardRank;
        this.pairHighCardRank = pairHighCardRank;
    }

    @Override
    public Name getName() {
        return NAME;
    }

    @Override
    public int compareTo(PokerHand o) {
        int cmp1 = NAME.compareTo(o.getName());
        if (cmp1 == 0) {
            FullHouse fullHouse = (FullHouse) o;
            int cmp2 = threeOfAKindHighCardRank.compareTo(fullHouse.threeOfAKindHighCardRank);
            if (cmp2 == 0) {
                return pairHighCardRank.compareTo(fullHouse.pairHighCardRank);
            } else {
                return cmp2;
            }
        } else {
            return cmp1;
        }
    }
}
