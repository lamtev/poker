package com.lamtev.poker.core.event_listeners;

public interface PlayerMoneyUpdatedListener {
    void playerMoneyUpdated(String playerId, int playerStack, int playerWager);
}
