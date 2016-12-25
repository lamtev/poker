package com.lamtev.poker.core.api;

public interface WagerPlacedListener {
    void wagerPlaced(String playerId, PlayerMoney playerMoney, int bank);
}
