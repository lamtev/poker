package com.lamtev.poker.core.hands;

import com.lamtev.poker.core.model.Rank;

import static com.lamtev.poker.core.hands.PokerHand.Name.FULL_HOUSE;

class FullHouse implements PokerHand {

    private final Name NAME = FULL_HOUSE;
    private final Rank threeOfAKindHighCardRank;
    private final Rank pairHighCardRank;

    FullHouse(Rank threeOfAKindHighCardRank, Rank pairHighCardRank) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FullHouse)) return false;

        FullHouse fullHouse = (FullHouse) o;

        if (NAME != fullHouse.NAME) return false;
        if (threeOfAKindHighCardRank != fullHouse.threeOfAKindHighCardRank) return false;
        return pairHighCardRank == fullHouse.pairHighCardRank;
    }

    @Override
    public int hashCode() {
        int result = NAME.hashCode();
        result = 31 * result + (threeOfAKindHighCardRank != null ? threeOfAKindHighCardRank.hashCode() : 0);
        result = 31 * result + (pairHighCardRank != null ? pairHighCardRank.hashCode() : 0);
        return result;
    }
}
