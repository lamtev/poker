package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.Poker;

class WinnersDeterminingPokerState implements PokerState {

    private Poker poker;

    WinnersDeterminingPokerState(Poker poker) {
        this.poker = poker;
    }

    @Override
    public void setBlinds() {}

    @Override
    public void call() throws Exception {}

    @Override
    public void raise(int additionalWager) throws Exception {}

    @Override
    public void fold() {}

    @Override
    public void check() throws Exception {}
}
