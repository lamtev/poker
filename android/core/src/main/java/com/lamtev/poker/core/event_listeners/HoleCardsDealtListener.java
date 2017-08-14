package com.lamtev.poker.core.event_listeners;

import com.lamtev.poker.core.model.Card;

import java.util.List;
import java.util.Map;

public interface HoleCardsDealtListener {
    String id();

    void holeCardsDealt(Map<String, List<Card>> playerIdToCards);

    void holeCardsDealt(List<Card> holeCards);
}
