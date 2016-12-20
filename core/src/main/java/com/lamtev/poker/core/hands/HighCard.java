package com.lamtev.poker.core.hands;

import com.lamtev.poker.core.model.Rank;

import javax.print.attribute.standard.MediaSize;
import java.util.List;

import static com.lamtev.poker.core.hands.PokerHand.Name.HIGH_CARD;

public class HighCard implements PokerHand {

    private final Name NAME = HIGH_CARD;
    private final List<Rank> cardsRanks;

    public HighCard(List<Rank> cardsRanks) {
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
}
