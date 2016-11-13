package com.lamtev.poker.core;

public interface PokerState {
    void nextState(OnNextStateListener onNextStateListener);
    void setBlinds();
    void call();
    void raise(int additionalWager);
    void fold();
    void check();
}
