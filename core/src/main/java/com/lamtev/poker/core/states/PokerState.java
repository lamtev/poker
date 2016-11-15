package com.lamtev.poker.core.states;

public interface PokerState {
    void setBlinds() throws Exception;

    void call() throws Exception;

    void raise(int additionalWager) throws Exception;

    void fold() throws Exception;

    void check() throws Exception;
}
