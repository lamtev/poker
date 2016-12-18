package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Rank;

import static com.lamtev.poker.core.combinations.PokerCombination.Name.THREE_OF_A_KIND;

public class ThreeOfAKind implements PokerCombination {

    private final Name NAME = THREE_OF_A_KIND;
    private final Rank highCardRank;
    private final Rank kicker;

    public ThreeOfAKind(Rank highCardRank, Rank kicker) {
        this.highCardRank = highCardRank;
        this.kicker = kicker;
    }

    @Override
    public Name getName() {
        return NAME;
    }

    @Override
    public int compareTo(PokerCombination o) {
        int cmp1 = NAME.compareTo(o.getName());
        if (cmp1 == 0) {
            ThreeOfAKind threeOfAKind = (ThreeOfAKind) o;
            int cmp2 = highCardRank.compareTo(threeOfAKind.highCardRank);
            if (cmp2 == 0) {
                return kicker.compareTo(threeOfAKind.kicker);
            } else {
                return cmp2;
            }
        } else {
            return cmp1;
        }
    }
}
