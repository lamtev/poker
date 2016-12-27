package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

class PreflopWageringPokerState extends WageringPokerState {

    PreflopWageringPokerState(Poker poker,
                              Players players, Bank bank, Dealer dealer, Cards commonCards) {
        super(poker, players, bank, dealer, commonCards);
        dealer.makePreflop();
        Map<String, Cards> playerIdToCards = new LinkedHashMap<>();
        players.forEach(player -> playerIdToCards.put(player.getId(), player.getCards()));
        poker.notifyPreflopMadeListeners(playerIdToCards);
    }

    @Override
    public void check() throws Exception {
        throw new Exception("can't check");
    }

    @Override
    public void attemptNextState() throws Exception {
        if (timeToShowDown()) {
            dealer.makeFlop();
            dealer.makeTurn();
            dealer.makeRiver();
            poker.notifyCommunityCardsListeners(new ArrayList<Card>() {{
                commonCards.forEach(this::add);
            }});
            poker.setState(new ShowdownPokerState(this, latestAggressorIndex()));
        } else if (timeToNextState()) {
            poker.setState(new FlopWageringPokerState(this));
        }
    }

    @Override
    boolean timeToNextState() {
        return preflopWageringHasBeenFinished();
    }
}
