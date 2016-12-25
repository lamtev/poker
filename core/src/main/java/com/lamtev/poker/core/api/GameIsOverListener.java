package com.lamtev.poker.core.api;

import java.util.List;

public interface GameIsOverListener {
    void gameIsOver(List<PlayerInfo> playersInfo);
}
