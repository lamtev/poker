package com.lamtev.poker.core.states;

import com.lamtev.poker.core.exceptions.RoundOfPlayIsOverException;

class RoundOfPlayIsOverState extends AbstractPokerState {

    @Override
    public void start() {

    }

    @Override
    public void call() throws RoundOfPlayIsOverException {
        throw new RoundOfPlayIsOverException();
    }

    @Override
    public void raise(int additionalWager) throws RoundOfPlayIsOverException {
        throw new RoundOfPlayIsOverException();
    }

    @Override
    public void allIn() throws RoundOfPlayIsOverException {
        throw new RoundOfPlayIsOverException();
    }

    @Override
    public void fold() throws RoundOfPlayIsOverException {
        throw new RoundOfPlayIsOverException();
    }

    @Override
    public void check() throws RoundOfPlayIsOverException {
        throw new RoundOfPlayIsOverException();
    }

    @Override
    public void showDown() throws RoundOfPlayIsOverException {
        throw new RoundOfPlayIsOverException();
    }

}
