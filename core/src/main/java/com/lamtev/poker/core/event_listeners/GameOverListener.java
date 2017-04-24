package com.lamtev.poker.core.event_listeners;

import com.lamtev.poker.core.api.PlayerIdStack;
import com.lamtev.poker.core.states.exceptions.GameOverException;

import java.util.List;

public interface GameOverListener {
    void gameOver(List<PlayerIdStack> playersInfo);
}
