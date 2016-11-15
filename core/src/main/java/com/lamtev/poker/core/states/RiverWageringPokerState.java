package com.lamtev.poker.core.states;

import com.lamtev.poker.core.model.Poker;

class RiverWageringPokerState extends WageringState {

    private Poker poker;

    RiverWageringPokerState(Poker poker) {
        super(poker);
        this.poker = poker;
        poker.getDealer().makeRiver();
    }

    @Override
    public void call() throws Exception {
        super.call();
        if (isTimeToNextState()) {
            nextState();
        }
    }

    @Override
    public void fold() {
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
        poker.update(() -> poker.setState(new WinnersDeterminingPokerState(poker)));
    }

}
