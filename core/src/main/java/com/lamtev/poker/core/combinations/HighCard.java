package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Rank;

import static com.lamtev.poker.core.combinations.PokerCombination.Name.HIGH_CARD;

public class HighCard implements PokerCombination {

    private final Name NAME = HIGH_CARD;
    private final Rank highCardRank;
    private final Rank kicker;

    public HighCard(Rank highCardRank, Rank kicker) {
        this.highCardRank = highCardRank;
        this.kicker = kicker;
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
