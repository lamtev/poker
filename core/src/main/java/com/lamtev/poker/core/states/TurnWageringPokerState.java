package com.lamtev.poker.core.states;

import com.lamtev.poker.core.model.Card;

import java.util.ArrayList;
import java.util.List;

class TurnWageringPokerState extends WageringPokerState {

    TurnWageringPokerState(ActionPokerState state) {
        super(state);
        dealer.makeTurn();
        List<Card> addedCards = new ArrayList<>();
        addedCards.add(commonCards.cardAt(4));
        poker.notifyCommunityCardsListeners(addedCards);
    }

    @Override
    protected void attemptNextState() throws Exception {
        if (timeToShowDown()) {
            //TODO think about how to dispose of code duplicates
            dealer.makeRiver();
            List<Card> addedCards = new ArrayList<>();
            addedCards.add(commonCards.cardAt(5));
            poker.notifyCommunityCardsListeners(addedCards);
            poker.setState(new ShowdownPokerState(this, latestAggressorIndex()));
        } else if (timeToNextState()) {
            poker.setState(new RiverWageringPokerState(this));
        }
    }

}
