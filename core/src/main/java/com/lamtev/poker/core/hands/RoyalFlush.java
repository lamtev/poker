package com.lamtev.poker.core.hands;

import static com.lamtev.poker.core.hands.PokerHand.Name.ROYAL_FLUSH;

final class RoyalFlush implements PokerHand {

    private final Name NAME = ROYAL_FLUSH;

    @Override
    public Name getName() {
        return NAME;
    }

    @Override
    public int compareTo(PokerHand o) {
        return NAME.compareTo(o.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoyalFlush)) return false;

        RoyalFlush that = (RoyalFlush) o;

        return NAME == that.NAME;
    }

    @Override
    public int hashCode() {
        return NAME.hashCode();
    }

    @Override
    public String toString() {
        return "RoyalFlush{}";
    }

}
