package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Rank;
import com.lamtev.poker.core.model.Suit;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.lamtev.poker.core.combinations.Flush.isFlush;
import static com.lamtev.poker.core.combinations.PokerCombination.Name.STRAIGHT_FLUSH;
import static com.lamtev.poker.core.combinations.Straight.isStraightFromRank;

public class StraightFlush implements PokerCombination {

    private final Name NAME = STRAIGHT_FLUSH;
    private Rank highCardRank;

    //TODO think about combinations constructors!!!
    public StraightFlush(List<Card> cards) {
        highCardRank = cards.get(0).getRank();
    }

    public static boolean isStraightFlush(List<Card> cards) {
        //TODO fix
        Comparator<Card> comparatorByRank = Comparator.comparing(Card::getRank).reversed();
        cards.sort(comparatorByRank);
        for (int i = 0; i < cards.size(); ++i) {
            int numberOfSameSuits = 1;
            Suit suit = cards.get(i).getSuit();
            for (int j = 0; j < cards.size(); ++j) {
                if (j != i && cards.get(j).getSuit().equals(suit) && ++numberOfSameSuits == 5) {
                    //TODO think about how to do it better
                    if (isStraightFromRank(cards, cards.get(i).getRank(), comparatorByRank)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Name getName() {
        return NAME;
    }

    @Override
    public int compareTo(PokerCombination o) {
        int cmp = NAME.compareTo(o.getName());
        if (cmp == 0) {
            return highCardRank.compareTo(((StraightFlush) o).highCardRank);
        } else {
            return cmp;
        }
    }
}
