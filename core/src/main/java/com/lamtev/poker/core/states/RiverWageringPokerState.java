package com.lamtev.poker.core.states;

class RiverWageringPokerState extends WageringPokerState {

    RiverWageringPokerState(ActionPokerState state) {
        super(state);
        dealer.makeRiver();
    }

    protected void attemptNextState() throws Exception {
        super.attemptNextState();
        if (timeToNextState()) {
            setState(new ShowdownPokerState(this, latestAggressorIndex()));
        }
    }

}
