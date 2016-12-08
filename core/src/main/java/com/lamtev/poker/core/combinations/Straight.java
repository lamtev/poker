package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Rank;
import com.lamtev.poker.core.model.Suit;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.lamtev.poker.core.combinations.PokerCombination.Name.STRAIGHT;

public class Straight implements PokerCombination {

    private final Name NAME = STRAIGHT;
    private Rank highCardRank;

    public Straight(List<Card> cards) {
        highCardRank = cards.get(0).getRank();
    }

    static boolean isStraight(List<Card> cards) {
        Comparator<Card> comparatorByRank = Comparator.comparing(Card::getRank).reversed();
        cards.sort(comparatorByRank);
        for (int i = Rank.ACE.ordinal(); i >= Rank.FIVE.ordinal(); --i) {
            if (isStraightFromRank(cards, Rank.values()[i], comparatorByRank)) {
                return true;
            }
        }
        return false;
    }

    static boolean isStraightFromRank(List<Card> cards, Rank rank, Comparator<Card> comparatorByRank) {
        int numberOfSequentialRanks = 0;
        int highCardIndex = 0;
        for (int i = rank.ordinal(); i >= - 1 && i >= rank.ordinal() - 4; --i) {
            int rankIndex = i == -1 ? Rank.ACE.ordinal() : i;
            Card card = new Card(Rank.values()[rankIndex], Suit.HEARTS);
            int keyIndex = Collections.binarySearch(cards, card, comparatorByRank);
            if (keyIndex >= 0) {
                ++numberOfSequentialRanks;
                highCardIndex = numberOfSequentialRanks == 1 ? keyIndex : highCardIndex;
            }
        }
        if (numberOfSequentialRanks == 5) {
            //TODO think about how to do it better
            Collections.swap(cards, 0, highCardIndex);
            cards.forEach(System.out::println);
            System.out.println();
            return true;
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
            Straight straight = (Straight) o;
            return highCardRank.compareTo(straight.highCardRank);
        } else {
            return cmp;
        }
    }
}
