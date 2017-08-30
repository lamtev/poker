package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.model.Dealer;

import java.util.ArrayList;
import java.util.List;

final class FlopWageringState extends WageringState {

    FlopWageringState(ActionState state) {
        super(state);
    }

    static void makeDealerJob(Poker poker, Dealer dealer, Cards communityCards) {
        dealer.makeFlop();
        List<Card> addedCards = new ArrayList<>();
        communityCards.forEach(addedCards::add);
        poker.notifyCommunityCardsDealtListeners(addedCards);
    }

    @Override
    void makeDealerJob() {
        makeDealerJob(poker, dealer, communityCards);
    }

    @Override
    boolean attemptNextState() {
        if (timeToForcedShowdown()) {
            TurnWageringState.makeDealerJob(poker, dealer, communityCards);
            RiverWageringState.makeDealerJob(poker, dealer, communityCards);
            if (latestAggressor() == null) {
                players.nextActiveAfterDealer();
            } else {
                players.setLatestAggressor(latestAggressor());
            }
            poker.setState(new ShowdownState(this, latestAggressor()));
            return true;
        } else if (timeToNextState()) {
            poker.setState(new TurnWageringState(this));
            return true;
        }
        return false;
    }

}
