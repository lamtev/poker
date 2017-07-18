package com.lamtev.poker.core.api;

import com.lamtev.poker.core.event_listeners.HoleCardsDealtListener;
import com.lamtev.poker.core.event_listeners.MoneyChangedListener;

public interface PokerPlayer extends HoleCardsDealtListener,
        MoneyChangedListener {
}
