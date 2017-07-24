package com.lamtev.poker.core.states;

import com.lamtev.poker.core.model.Card;

import java.util.ArrayList;
import java.util.List;

final class FlopWageringState extends WageringState {

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
    boolean attemptNextState() {
        if (timeToForcedShowdown()) {
            //TODO think about how to dispose of code duplicates
            dealer().makeTurn();
            dealer().makeRiver();
            List<Card> addedCards = new ArrayList<>();
            addedCards.add(communityCards().cardAt(4));
            addedCards.add(communityCards().cardAt(5));
            poker().notifyCommunityCardsDealtListeners(addedCards);
            if (latestAggressor() == null) {
                players().nextActiveAfterDealer();
            } else {
                players().setLatestAggressor(latestAggressor());
            }
            poker().setState(new ShowdownState(this, latestAggressor()));
            return true;
        } else if (timeToNextState()) {
            poker().setState(new TurnWageringState(this));
            return true;
        }
        return false;
    }

}
