package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Rank;

import java.util.Comparator;
import java.util.List;

import static com.lamtev.poker.core.combinations.Flush.isFlush;
import static com.lamtev.poker.core.combinations.PokerCombination.Name.STRAIGHT_FLUSH;
import static com.lamtev.poker.core.combinations.Straight.isStraight;
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
        boolean isFlush = isFlush(cards);
        Card flushHighCard = cards.get(0);
        boolean isStraight = isStraight(cards);
        Card straightHighCard = cards.get(0);
        return isFlush && isStraight && flushHighCard.equals(straightHighCard);
    }


    //TODO validate
    static boolean isStraightFlushFromRank(List<Card> cards, Rank rank, Comparator<Card> comparatorByRank) {
        return isStraightFromRank(cards, rank, comparatorByRank) && isFlush(cards);
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
