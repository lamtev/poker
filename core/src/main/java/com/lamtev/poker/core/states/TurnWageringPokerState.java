package com.lamtev.poker.core.states;

class TurnWageringPokerState extends WageringPokerState {

    TurnWageringPokerState(ActionPokerState state) {
        super(state.poker, state.players, state.bank, state.dealer, state.commonCards);
        dealer.makeTurn();
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
        poker.setState(new RiverWageringPokerState(this));
    }

}
