package com.lamtev.poker.core.states;

import com.lamtev.poker.core.model.Card;

import java.util.ArrayList;
import java.util.List;

class RiverWageringPokerState extends WageringPokerState {

    RiverWageringPokerState(ActionPokerState state) {
        super(state);
        dealer.makeRiver();
        List<Card> addedCards = new ArrayList<>();
        addedCards.add(commonCards.cardAt(5));
        poker.notifyCommunityCardsChangedListeners(addedCards);
    }

    @Override
    protected void attemptNextState() throws Exception {
        if (timeToNextState()) {
            poker.setState(new ShowdownPokerState(this, latestAggressorIndex()));
        }
    }

}
