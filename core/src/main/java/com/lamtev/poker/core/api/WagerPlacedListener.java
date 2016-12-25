package com.lamtev.poker.core.api;

public interface WagerPlacedListener {
    void dataChanged(String playerId, int stack, int wager, int bank);
}
