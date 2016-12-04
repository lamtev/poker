package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.model.Suit;

import static com.lamtev.poker.core.combinations.PokerCombination.Name.FLUSH;

public class Flush implements PokerCombination {

    private final Name NAME = FLUSH;

    public static boolean isFlush(Cards cards) {
        Suit suit = cards.cardAt(0).getSuit();
        int numberOfSameSuits = 1;
        for (Card card : cards) {
            if (!card.getSuit().equals(suit)) {
                ++numberOfSameSuits;
            }
        }
        return numberOfSameSuits >= 5;
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
