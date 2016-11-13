package com.lamtev.poker.core;

public abstract class WageringState implements PokerState {

    private Players players;
    private Bank bank;

    public WageringState(Poker poker) {
        this.players = poker.players();
        this.bank = poker.bank();
    }

    @Override
    public void nextState(OnNextStateListener onNextStateListener) {
        onNextStateListener.nextState();
    }

    @Override
    public void setBlinds() {}

    @Override
    public void call() {

    }

    @Override
    public void raise(int additionalWager) {

    }

    @Override
    public void fold() {

    }

    @Override
    public void check() {

    }
}
