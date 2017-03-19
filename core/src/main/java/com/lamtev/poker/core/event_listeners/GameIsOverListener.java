package com.lamtev.poker.core.event_listeners;

import com.lamtev.poker.core.api.PlayerIdStack;

import java.util.List;

public interface GameIsOverListener {
    void gameIsOver(List<PlayerIdStack> playersInfo);
}
