package com.lamtev.poker.core;

public class BlindsPokerState implements PokerState {

    private Players players;
    private Bank bank;
    private int smallBlindSize;

    public BlindsPokerState(Poker poker) {
        players = poker.players();
        bank = poker.bank();
        smallBlindSize = poker.smallBlindSize();
    }

    @Override
    public void nextState(OnNextStateListener onNextStateListener) {
        onNextStateListener.nextState();
    }

    @Override
    public void setBlinds() {
        bank.acceptBlindWagers(smallBlindSize);
    }

    @Override
    public void call() {}

    @Override
    public void raise(int additionalWager) {}

    @Override
    public void fold() {}

    @Override
    public void check() {}

}
