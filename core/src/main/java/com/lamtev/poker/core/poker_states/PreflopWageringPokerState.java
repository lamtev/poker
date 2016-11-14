package com.lamtev.poker.core.poker_states;

import com.lamtev.poker.core.Poker;

class PreflopWageringPokerState extends WageringState {

    private Poker poker;

    PreflopWageringPokerState(Poker poker) {
        super(poker);
        this.poker = poker;
        poker.getDealer().makePreflop();
    }

    @Override
    public void setBlinds() {
        super.setBlinds();
        if (isTimeToNextState()) {
            nextState();
        }
    }

    @Override
    public void call() throws Exception {
        super.call();
        if (isTimeToNextState()) {
            nextState();
        }
    }

    @Override
    public void raise(int additionalWager) throws Exception {
        super.raise(additionalWager);
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

    private boolean isTimeToNextState() {
        return System.currentTimeMillis() == 1111L;
    }

    private void nextState() {
        poker.update((state) -> state = new FlopWageringPokerState(poker));
    }

}
