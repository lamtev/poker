package com.lamtev.poker.core.hands;

import com.lamtev.poker.core.model.Rank;

import java.util.List;

import static com.lamtev.poker.core.hands.PokerHand.Name.THREE_OF_A_KIND;

class ThreeOfAKind implements PokerHand {

    private final Name NAME = THREE_OF_A_KIND;
    private final Rank highCardRank;
    private final List<Rank> otherCardsRanks;

    ThreeOfAKind(Rank highCardRank, List<Rank> otherCardsRanks) {
        this.highCardRank = highCardRank;
        this.otherCardsRanks = otherCardsRanks;
    }

    @Override
    public Name getName() {
        return NAME;
    }

    @Override
    public int compareTo(PokerHand o) {
        int cmp1 = NAME.compareTo(o.getName());
        if (cmp1 == 0) {
            ThreeOfAKind threeOfAKind = (ThreeOfAKind) o;
            int cmp2 = highCardRank.compareTo(threeOfAKind.highCardRank);
            if (cmp2 == 0) {
                int i = 0;
                for (Rank rank : otherCardsRanks) {
                    int cmpi = rank.compareTo(threeOfAKind.otherCardsRanks.get(i));
                    if (cmpi != 0) {
                        return cmpi;
                    }
                    ++i;
                }
                return 0;
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
        if (!(o instanceof ThreeOfAKind)) return false;

        ThreeOfAKind that = (ThreeOfAKind) o;

        if (NAME != that.NAME) return false;
        if (highCardRank != that.highCardRank) return false;
        return otherCardsRanks != null ? otherCardsRanks.equals(that.otherCardsRanks) : that.otherCardsRanks == null;
    }

    @Override
    public int hashCode() {
        int result = NAME.hashCode();
        result = 31 * result + (highCardRank != null ? highCardRank.hashCode() : 0);
        result = 31 * result + (otherCardsRanks != null ? otherCardsRanks.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ThreeOfAKind{" +
                "highCardRank=" + highCardRank +
                ", otherCardsRanks=" + otherCardsRanks +
                '}';
    }

}

