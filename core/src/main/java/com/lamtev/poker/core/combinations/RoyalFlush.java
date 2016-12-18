package com.lamtev.poker.core.combinations;

import static com.lamtev.poker.core.combinations.PokerCombination.Name.ROYAL_FLUSH;

public class RoyalFlush implements PokerCombination {

    private final Name NAME = ROYAL_FLUSH;

    @Override
    public Name getName() {
        return NAME;
    }

    @Override
    public int compareTo(PokerCombination o) {
        return NAME.compareTo(o.getName());
    }

}
