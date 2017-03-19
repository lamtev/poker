package com.lamtev.poker.core.event_listeners;

import com.lamtev.poker.core.hands.PokerHand;

public interface PlayerShowedDownListener {
    void playerShowedDown(String playerId, PokerHand hand);
}
