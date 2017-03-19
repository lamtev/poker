package com.lamtev.poker.core.event_listeners;

import com.lamtev.poker.core.api.PlayerMoney;

public interface WagerPlacedListener {
    void wagerPlaced(String playerId, PlayerMoney playerMoney, int bank);
}
