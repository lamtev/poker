package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Cards;

import java.util.Comparator;

import static com.lamtev.poker.core.combinations.PokerCombination.Name.STRAIGHT;

public class Straight implements PokerCombination {

    private final Name NAME = STRAIGHT;

    public static boolean isStraight(Cards cards) {
        Comparator<Card> comparatorByRank = Comparator.comparing(Card::getRank);
        cards.sort(comparatorByRank);
        //TODO
        return true;
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
