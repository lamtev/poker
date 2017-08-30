package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.model.Dealer;

import static java.util.Collections.singletonList;

final class RiverWageringState extends WageringState {

    RiverWageringState(ActionState state) {
        super(state);
    }

    static void makeDealerJob(Poker poker, Dealer dealer, Cards communityCards) {
        dealer.makeRiver();

        poker.notifyCommunityCardsDealtListeners(singletonList(communityCards.cardAt(5)));
    }

    @Override
    void makeDealerJob() {
        makeDealerJob(poker, dealer, communityCards);
    }

    @Override
    boolean attemptNextState() {
        if (timeToNextState()) {
            poker.setState(new ShowdownState(this, latestAggressor()));
            return true;
        }
        return false;
    }

}
