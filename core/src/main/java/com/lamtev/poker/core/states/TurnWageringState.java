package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.model.Dealer;

import static java.util.Collections.singletonList;

final class TurnWageringState extends WageringState {

    TurnWageringState(ActionState state) {
        super(state);
    }

    static void makeDealerJob(Poker poker, Dealer dealer, Cards communityCards) {
        dealer.makeTurn();
        poker.notifyCommunityCardsDealtListeners(singletonList(communityCards.cardAt(4)));
    }

    @Override
    void makeDealerJob() {
        makeDealerJob(poker, dealer, communityCards);
    }

    @Override
    boolean attemptNextState() {
        if (timeToForcedShowdown()) {
            RiverWageringState.makeDealerJob(poker, dealer, communityCards);
            poker.setState(new ShowdownState(this, latestAggressor()));
            return true;
        } else if (timeToNextState()) {
            poker.setState(new RiverWageringState(this));
            return true;
        }
        return false;
    }

}
