package com.lamtev.poker.core.hands;

import com.lamtev.poker.core.model.Rank;

import java.util.List;

import static com.lamtev.poker.core.hands.PokerHand.Name.HIGH_CARD;

class HighCard implements PokerHand {

    private final Name NAME = HIGH_CARD;
    private final List<Rank> cardsRanks;

    HighCard(List<Rank> cardsRanks) {
        this.cardsRanks = cardsRanks;
    }

    @Override
    public Name getName() {
        return NAME;
    }

    @Override
    public int compareTo(PokerHand o) {
        int cmp = NAME.compareTo(o.getName());
        if (cmp == 0) {
            HighCard highCard = (HighCard) o;
            int i = 0;
            for (Rank rank : cardsRanks) {
                int cmpi = rank.compareTo(highCard.cardsRanks.get(i));
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
        if (o == null) return false;
        if (!(o instanceof HighCard)) return false;

        HighCard highCard = (HighCard) o;

        if (NAME != highCard.NAME) return false;
        return cardsRanks != null && cardsRanks.equals(highCard.cardsRanks);
    }

    @Override
    public int hashCode() {
        int result = NAME.hashCode();
        result = 31 * result + (cardsRanks != null ? cardsRanks.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HighCard{" +
                "cardsRanks=" + cardsRanks +
                '}';
    }

}
