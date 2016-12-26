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
        poker.notifyCommunityCardsListeners(addedCards);
    }

    protected void attemptNextState() throws Exception {
        //super.attemptNextState();
        if (timeToShowDown()) {
            dealer.makeTurn();
            dealer.makeRiver();
            setState(new ShowdownPokerState(this, latestAggressorIndex()));
        }
        else if (timeToNextState()) {
            setState(new TurnWageringPokerState(this));
        }
    }

}
