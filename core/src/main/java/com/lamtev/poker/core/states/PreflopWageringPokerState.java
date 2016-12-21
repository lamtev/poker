package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.Bank;
import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.model.Dealer;
import com.lamtev.poker.core.model.Players;

class PreflopWageringPokerState extends WageringPokerState {

    PreflopWageringPokerState(Poker poker,
                              Players players, Bank bank, Dealer dealer, Cards commonCards) {
        super(poker, players, bank, dealer, commonCards);
        dealer.makePreflop();
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
