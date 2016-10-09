package com.lamtev.poker.core;

public interface PokerAPI {
    void setUpNumberOfPlayers(int numberOfPlayers);
    void setUpSmallBlind(int smallBlind);
    void setUpBigBlind(int bigBlind);
    void placeWager(int wager);
    void raise(int additionalWager);
    void check();
    void fold();
}
