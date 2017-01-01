package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.PlayerIdStack;

import java.util.List;

public interface PokerState {

    void setUp(List<PlayerIdStack> playersInfo, String smallBlindId, String bigBlindId, int smallBlindSize);

    void call() throws Exception;

    void raise(int additionalWager) throws Exception;

    void allIn() throws Exception;

    void fold() throws Exception;

    void check() throws Exception;

    void showDown() throws Exception;
}
