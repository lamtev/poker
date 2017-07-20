package com.lamtev.poker.core.event_listeners;

import com.lamtev.poker.core.api.RoundOfPlay;

public interface RoundOfPlayChangedListener {
    void roundOfPlayChanged(RoundOfPlay roundOfPlay);
}
