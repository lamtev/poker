package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Rank;

import java.util.Comparator;
import java.util.List;

import static com.lamtev.poker.core.combinations.PokerCombination.Name.ROYAL_FLUSH;
import static com.lamtev.poker.core.combinations.StraightFlush.isStraightFlushFromRank;

public class RoyalFlush implements PokerCombination {

    private final Name NAME = ROYAL_FLUSH;

    public static boolean isRoyalFlush(List<Card> cards) {
        Comparator<Card> comparatorByRank = Comparator.comparing(Card::getRank).reversed();
        cards.sort(comparatorByRank);
        return isStraightFlushFromRank(cards, Rank.ACE, comparatorByRank);
    }

    @Override
    public Name getName() {
        return NAME;
    }

    @Override
    public int compareTo(PokerCombination o) {
        return 0;
    }

}
