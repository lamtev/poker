package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.Poker;

class PreflopWageringPokerState extends WageringState {

    private Poker poker;

    PreflopWageringPokerState(Poker poker) {
        super(poker);
        this.poker = poker;
        //TODO think about replacing line below by anonymous class or lambda
        poker.getDealer().makePreflop();
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
    public void check() throws Exception {
        super.check();
        if (isTimeToNextState()) {
            nextState();
        }
    }

    private void nextState() {
        poker.update(() -> poker.setState(new FlopWageringPokerState(poker)));
    }

}
