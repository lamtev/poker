package com.lamtev.poker.core.states;

class RiverWageringPokerState extends WageringPokerState {

    RiverWageringPokerState(ActionPokerState state) {
        super(state);
        dealer.makeRiver();
    }

    @Override
    public void call() throws Exception {
        super.call();
        if (timeToNextState()) {
            nextState();
        }
    }

    @Override
    public void fold() throws Exception {
        super.fold();
        if (timeToNextState()) {
            nextState();
        }
    }

    @Override
    public void check() throws Exception {
        super.check();
        if (timeToNextState()) {
            nextState();
        }
    }

    private void nextState() throws Exception {
        setState(new ShowdownPokerState(this, latestAggressorIndex()));
    }

}
