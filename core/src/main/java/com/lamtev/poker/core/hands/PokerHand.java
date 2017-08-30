package com.lamtev.poker.core.hands;

import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Cards;

import java.util.List;

public interface PokerHand extends Comparable<PokerHand> {

    static PokerHand of(List<Card> cards) {
        return PokerHandFactory.createCombination(cards);
    }

    static PokerHand of(Cards playerCards, Cards communityCards) {
        return PokerHandFactory.createCombination(playerCards, communityCards);
    }

    static PokerHand of(List<Card> playerCards, List<Card> communityCards) {
        return PokerHandFactory.createCombination(playerCards, communityCards);
    }

    Name getName();

    enum Name {
        HIGH_CARD,
        PAIR,
        TWO_PAIRS,
        THREE_OF_A_KIND,
        STRAIGHT,
        FLUSH,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        STRAIGHT_FLUSH,
        ROYAL_FLUSH
    }

}
