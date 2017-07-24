package com.lamtev.poker.core.states;

import com.lamtev.poker.core.model.Card;

import java.util.ArrayList;
import java.util.List;

final class TurnWageringState extends WageringState {

    TurnWageringState(ActionState state) {
        super(state);
    }

    @Override
    void makeDealerJob() {
        dealer().makeTurn();
        List<Card> addedCards = new ArrayList<>();
        addedCards.add(communityCards().cardAt(4));
        poker().notifyCommunityCardsDealtListeners(addedCards);
    }

    @Override
    boolean attemptNextState() {
        if (timeToForcedShowdown()) {
            //TODO think about how to dispose of code duplicates
            dealer().makeRiver();
            List<Card> addedCards = new ArrayList<>();
            addedCards.add(communityCards().cardAt(5));
            poker().notifyCommunityCardsDealtListeners(addedCards);
            poker().setState(new ShowdownState(this, latestAggressor()));
            return true;
        } else if (timeToNextState()) {
            poker().setState(new RiverWageringState(this));
            return true;
        }
        return false;
    }

}
