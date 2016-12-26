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

    protected void attemptNextState() throws Exception {
        //super.attemptNextState();
//        if (wasAllIn()) {
//            return;
//        }
        if (timeToShowDown()) {
            dealer.makeRiver();
            setState(new ShowdownPokerState(this, latestAggressorIndex()));
        }
        else if (timeToNextState()) {
            setState(new RiverWageringPokerState(this));
        }
    }

}
