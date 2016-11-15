package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.Poker;

class TurnWageringPokerState extends WageringState {

    private Poker poker;

    TurnWageringPokerState(Poker poker) {
        super(poker);
        this.poker = poker;
        poker.getDealer().makeTurn();
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
        poker.update(() -> poker.setState(new RiverWageringPokerState(poker)));
    }

}
