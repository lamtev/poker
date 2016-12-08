package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Rank;
import com.lamtev.poker.core.model.Suit;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.lamtev.poker.core.combinations.PokerCombination.Name.FLUSH;

public class Flush implements PokerCombination {

    private final Name NAME = FLUSH;
    private final Rank highCardRank;

    public static boolean isFlush(List<Card> cards) {
        Comparator<Card> comparatorByRank = Comparator.comparing(Card::getRank).reversed();
        cards.sort(comparatorByRank);
        for (int i = 0; i < cards.size(); ++i) {
            int numberOfSameSuits = 1;
            Suit suit = cards.get(i).getSuit();
            for (int j = 0; j < cards.size(); ++j) {
                if (j != i && cards.get(j).getSuit().equals(suit) && ++numberOfSameSuits == 5) {
                    //TODO think about how to do it better
                    Collections.swap(cards, 0, i);
                    return true;
                }
            }
        }
        return false;
    }



    Flush(Rank highCardRank) {
        this.highCardRank = highCardRank;
    }

    @Override
    public Name getName() {
        return NAME;
    }

    @Override
    public int compareTo(PokerCombination o) {
        int cmp = NAME.compareTo(o.getName());
        if (cmp == 0) {
            Flush flush = (Flush) o;
            return highCardRank.compareTo(flush.highCardRank);
        } else {
            return cmp;
        }
    }
}
