package com.lamtev.poker.core.states;

import com.lamtev.poker.core.model.Card;

import java.util.ArrayList;
import java.util.List;

class FlopWageringState extends WageringState {

    FlopWageringState(ActionState state) {
        super(state);
    }

    @Override
    void makeDealerJob() {
        dealer().makeFlop();
        List<Card> addedCards = new ArrayList<>();
        communityCards().forEach(addedCards::add);
        poker().notifyCommunityCardsDealtListeners(addedCards);
    }

    @Override
    protected void attemptNextState() {
        if (timeToForcedShowdown()) {
            //TODO think about how to dispose of code duplicates
            dealer().makeTurn();
            dealer().makeRiver();
            List<Card> addedCards = new ArrayList<>();
            addedCards.add(communityCards().cardAt(4));
            addedCards.add(communityCards().cardAt(5));
            poker().notifyCommunityCardsDealtListeners(addedCards);
            poker().setState(new ShowdownState(this, latestAggressor()));
        } else if (timeToNextState()) {
            poker().setState(new TurnWageringState(this));
        }
    }

    @Override
    void determineUnderTheGunPosition() {
        players().nextNonAllinnerAfterDealer();
    }

}
