package com.lamtev.poker.core.event_listeners;

public interface MoneyChangedListener {
    void onMoneyChanged(String playerId, int playerStack, int playerWager, int bank);
}
