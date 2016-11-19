package com.lamtev.poker.core.states;

class FlopWageringPokerState extends WageringPokerState {

    FlopWageringPokerState(ActionPokerState state) {
        super(state);
        dealer.makeFlop();
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

    private void nextState() throws Exception {
        setState(new TurnWageringPokerState(this));
    }

}
