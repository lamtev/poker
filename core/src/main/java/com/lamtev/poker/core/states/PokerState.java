package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.PlayerInfo;

import java.util.List;

public interface PokerState {

    void setUp(List<PlayerInfo> playersInfo, int smallBlindSize);

    void call() throws Exception;

    void raise(int additionalWager) throws Exception;

    void allIn() throws Exception;

    void fold() throws Exception;

    void check() throws Exception;

    void showDown() throws Exception;
}
