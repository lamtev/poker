package com.lamtev.poker.core.states;

class FlopWageringPokerState extends WageringPokerState {

    FlopWageringPokerState(ActionPokerState state) {
        super(state);
        dealer.makeFlop();
    }

    protected void attemptNextState() throws Exception {
        super.attemptNextState();
        if (timeToNextState()) {
            setState(new TurnWageringPokerState(this));
        }
    }

}
