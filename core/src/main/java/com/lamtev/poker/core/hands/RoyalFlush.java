package com.lamtev.poker.core.hands;

import static com.lamtev.poker.core.hands.PokerHand.Name.ROYAL_FLUSH;

public class RoyalFlush implements PokerHand {

    private final Name NAME = ROYAL_FLUSH;

    @Override
    public Name getName() {
        return NAME;
    }

    @Override
    public int compareTo(PokerHand o) {
        return NAME.compareTo(o.getName());
    }

}
