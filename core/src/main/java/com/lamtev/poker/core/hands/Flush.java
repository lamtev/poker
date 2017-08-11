package com.lamtev.poker.core.hands;

import com.lamtev.poker.core.model.Rank;

import java.util.ArrayList;
import java.util.List;

import static com.lamtev.poker.core.hands.PokerHand.Name.FLUSH;

class Flush implements PokerHand {

    private final Name NAME = FLUSH;
    private final List<Rank> cardsRanks = new ArrayList<>();

    Flush(List<Rank> cardsRanks) {
        this.cardsRanks.addAll(cardsRanks);
    }

    @Override
    public Name getName() {
        return NAME;
    }

    @Override
    public int compareTo(PokerHand o) {
        int cmp = NAME.compareTo(o.getName());
        if (cmp == 0) {
            Flush flush = (Flush) o;
            int i = 0;
            for (Rank rank : cardsRanks) {
                int cmpi = rank.compareTo(flush.cardsRanks.get(i));
                if (cmpi != 0) {
                    return cmpi;
                }
                ++i;
            }
            return 0;
        } else {
            return cmp;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flush flush = (Flush) o;

        if (NAME != flush.NAME) return false;
        return cardsRanks.equals(flush.cardsRanks);
    }

    @Override
    public int hashCode() {
        int result = NAME.hashCode();
        result = 31 * result + cardsRanks.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Flush{" +
                "NAME=" + NAME +
                ", cardsRanks=" + cardsRanks +
                '}';
    }

}
