package com.lamtev.poker.core.states;

public interface PokerState {
    void setBlinds();
    void call() throws Exception;
    void raise(int additionalWager) throws Exception;
    void fold();
    void check() throws Exception;
}