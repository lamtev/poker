package com.lamtev.poker.core.event_listeners;

import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;

import java.util.List;

public interface PlayerShowedDownListener {
    void playerShowedDown(String playerId, List<Card> holeCards, PokerHand hand);

    void playerShowedDown(String playerId, String hand);
}
