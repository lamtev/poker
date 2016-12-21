package com.lamtev.poker.core.hands;

public interface PokerHand extends Comparable<PokerHand> {

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

    Name getName();

}
