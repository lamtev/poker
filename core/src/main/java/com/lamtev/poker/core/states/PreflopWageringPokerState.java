package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.Bank;
import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.model.Dealer;
import com.lamtev.poker.core.model.Players;

import java.util.HashMap;
import java.util.Map;

class PreflopWageringPokerState extends WageringPokerState {

    PreflopWageringPokerState(Poker poker,
                              Players players, Bank bank, Dealer dealer, Cards commonCards) {
        super(poker, players, bank, dealer, commonCards);
        dealer.makePreflop();
        Map<String, Cards> playerIdToCards = new HashMap<>();
        players.forEach(player -> playerIdToCards.put(player.getId(), player.getCards()));
        poker.notifyPreflopMadeListeners(playerIdToCards);
    }

    @Override
    public void check() throws Exception {
        throw new Exception();
    }

    public void attemptNextState() throws Exception {
        super.attemptNextState();
        if (preflopHasBeenFinished()) {
            setState(new FlopWageringPokerState(this));
        }
    }

}
