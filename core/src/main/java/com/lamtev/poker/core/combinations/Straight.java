package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Rank;
import com.lamtev.poker.core.model.Suit;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.lamtev.poker.core.combinations.PokerCombination.Name.STRAIGHT;

public class Straight implements PokerCombination {

    private final Name NAME = STRAIGHT;
    private Rank highCardRank;

    public Straight(List<Card> cards) {

    }

    public static boolean isStraight(List<Card> cards) {
        Comparator<Card> comparatorByRank = Comparator.comparing(Card::getRank).reversed();
        cards.sort(comparatorByRank);
        for (int i = Rank.ACE.ordinal(); i >= Rank.FIVE.ordinal(); --i) {
            int numberOfSequentialRanks = 0;
            for (int j = i; j >= i - 4; --j) {
                int rankIndex = j == -1 ? Rank.ACE.ordinal() : j;
                Card card = new Card(Rank.values()[rankIndex], Suit.HEARTS);
                int keyIndex = Collections.binarySearch(cards, card, comparatorByRank);
                if (keyIndex >= 0 && ++numberOfSequentialRanks == 5) {
                    return true;
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
        //TODO implement
        return 0;
    }
}
