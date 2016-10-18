package com.lamtev.poker.core;

public interface PokerAPI {
    void call();
    void raise(int additionalWager);
    void fold();
    void check();
}
