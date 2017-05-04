package com.lamtev.poker.core.states;

import com.lamtev.poker.core.model.Card;

import java.util.ArrayList;
import java.util.List;

class FlopWageringPokerState extends WageringPokerState {

    FlopWageringPokerState(ActionPokerState state) {
        super(state);
        dealer.makeFlop();
        List<Card> addedCards = new ArrayList<>();
        commonCards.forEach(addedCards::add);
        poker.notifyCommunityCardsChangedListeners(addedCards);
    }

    @Override
    protected void attemptNextState() {
        if (timeToShowDown()) {
            //TODO think about how to dispose of code duplicates
            dealer.makeTurn();
            dealer.makeRiver();
            List<Card> addedCards = new ArrayList<>();
            addedCards.add(commonCards.cardAt(4));
            addedCards.add(commonCards.cardAt(5));
            poker.notifyCommunityCardsChangedListeners(addedCards);
            poker.setState(new ShowdownPokerState(this, latestAggressorIndex()));
        } else if (timeToNextState()) {
            poker.setState(new TurnWageringPokerState(this));
        }
    }

}
