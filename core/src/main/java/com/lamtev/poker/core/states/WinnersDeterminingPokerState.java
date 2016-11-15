package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.states.exceptions.GameIsOverException;

class WinnersDeterminingPokerState implements PokerState {

    private Poker poker;

    WinnersDeterminingPokerState(Poker poker) {
        this.poker = poker;
        poker.update(() -> poker.setState(new PokerState() {
            @Override
            public void setBlinds() throws Exception {
                throw new GameIsOverException();
            }

            @Override
            public void call() throws Exception {
                throw new GameIsOverException();
            }

            @Override
            public void raise(int additionalWager) throws Exception {
                throw new GameIsOverException();
            }

            @Override
            public void fold() throws Exception {
                throw new GameIsOverException();
            }

            @Override
            public void check() throws Exception {
                throw new GameIsOverException();
            }
        }));
    }

    @Override
    public void setBlinds() throws Exception {
    }

    @Override
    public void call() throws Exception {
    }

    @Override
    public void raise(int additionalWager) throws Exception {
    }

    @Override
    public void fold() throws Exception {
    }

    @Override
    public void check() throws Exception {
    }
}
