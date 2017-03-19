package com.lamtev.poker.core.event_listeners;

import com.lamtev.poker.core.model.Cards;

import java.util.Map;

public interface PreflopMadeListener {
    void preflopMade(Map<String, Cards> playerIdToCards);
}
