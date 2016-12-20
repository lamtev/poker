package com.lamtev.poker.core.hands;

import com.lamtev.poker.core.model.Rank;

import java.util.List;

import static com.lamtev.poker.core.hands.PokerHand.Name.PAIR;

public class Pair implements PokerHand {

    private final Name NAME = PAIR;
    private final Rank highCardRank;
    private final List<Rank> otherCardsRanks;

    public Pair(Rank highCardRank, List<Rank> otherCardsRanks) {
        this.highCardRank = highCardRank;
        this.otherCardsRanks = otherCardsRanks;
    }

    @Override
    public Name getName() {
        return NAME;
    }

    //TODO test
    @Override
    public int compareTo(PokerHand o) {
        int cmp1 = NAME.compareTo(o.getName());
        if (cmp1 == 0) {
            Pair pair = (Pair) o;
            int cmp2 = highCardRank.compareTo(pair.highCardRank);
            if (cmp2 == 0) {
                int i = 0;
                for (Rank rank : otherCardsRanks) {
                    int cmpi = rank.compareTo(pair.otherCardsRanks.get(i));
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
