package com.lamtev.poker.core;

public interface PokerAPI {
    void setUpNumberOfPlayers(int numberOfPlayers);
    void setUpBlinds(int smallBlind);
    void call();
    void raise(int additionalWager);
    void fold();
    void check();
    void reRaise(int additionalWager);
}
