package com.lamtev.poker.core.event_listeners;

import com.lamtev.poker.core.model.Card;

import java.util.List;
import java.util.Map;

public interface PreflopMadeListener {
    void preflopMade(Map<String, List<Card>> playerIdToCards);
}
