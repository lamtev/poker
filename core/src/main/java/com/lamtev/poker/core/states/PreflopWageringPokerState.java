package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.Bank;
import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.model.Dealer;
import com.lamtev.poker.core.model.Players;

import java.util.List;

class PreflopWageringPokerState extends WageringPokerState {

    PreflopWageringPokerState(List<WageringEndListener> wageringEndListeners, Poker poker,
                              Players players, Bank bank, Dealer dealer, Cards commonCards) {
        super(wageringEndListeners, poker, players, bank, dealer, commonCards);
        dealer.makePreflop();
    }

    @Override
    public void call() throws Exception {
        super.call();
        if (isTimeToNextState()) {
            nextState();
        }
    }

    @Override
    public void fold() throws Exception {
        super.fold();
        if (isTimeToNextState()) {
            nextState();
        }
    }

    @Override
    boolean isTimeToNextState() {
        return preflopHasBeenFinished();
    }

    @Override
    public void check() throws Exception {
        throw new Exception();
    }

    private void nextState() throws Exception {
        setState(new FlopWageringPokerState(this));
    }

}
