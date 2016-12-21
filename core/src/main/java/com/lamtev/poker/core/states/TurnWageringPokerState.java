package com.lamtev.poker.core.states;

class TurnWageringPokerState extends WageringPokerState {

    TurnWageringPokerState(ActionPokerState state) {
        super(state);
        dealer.makeTurn();
    }

    protected void attemptNextState() throws Exception {
        super.attemptNextState();
        if (timeToNextState()) {
            setState(new RiverWageringPokerState(this));
        }
    }

}
