package com.lamtev.poker.core.api;

import com.lamtev.poker.core.hands.PokerHand;

public interface PlayerShowedDownListener {
    void playerShowedDown(String playerId, PokerHand hand);
}
