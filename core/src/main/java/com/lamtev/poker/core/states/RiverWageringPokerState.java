package com.lamtev.poker.core.states;

import com.lamtev.poker.core.model.Card;

import java.util.ArrayList;
import java.util.List;

class RiverWageringPokerState extends WageringPokerState {

    RiverWageringPokerState(ActionPokerState state) {
        super(state);
        System.out.println(commonCards.size());
        dealer.makeRiver();
        List<Card> addedCards = new ArrayList<>();
        addedCards.add(commonCards.cardAt(5));
        poker.notifyCommunityCardsListeners(addedCards);
    }

    protected void attemptNextState() throws Exception {
        //super.attemptNextState();
//        if (wasAllIn()) {
//            return;
//        }
        if (timeToNextState()) {
            setState(new ShowdownPokerState(this, latestAggressorIndex()));
        }
    }

}
