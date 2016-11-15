package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.Poker;

class FlopWageringPokerState extends WageringState {

    private Poker poker;

    FlopWageringPokerState(Poker poker) {
        super(poker);
        this.poker = poker;
        poker.getDealer().makeFlop();
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
        poker.update(() -> poker.setState(new TurnWageringPokerState(poker)));
    }

}
