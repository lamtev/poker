package com.lamtev.poker.core.hands;

import com.lamtev.poker.core.model.Rank;

import java.util.List;

import static com.lamtev.poker.core.hands.PokerHand.Name.THREE_OF_A_KIND;

public class ThreeOfAKind implements PokerHand {

    private final Name NAME = THREE_OF_A_KIND;
    private final Rank highCardRank;
    private final List<Rank> otherCardsRanks;

    public ThreeOfAKind(Rank highCardRank, List<Rank> otherCardsRanks) {
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
}
