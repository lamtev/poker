package com.lamtev.poker.core.api;

import java.util.List;

public interface PokerAPI {
    void subscribe(PokerEventListener pokerEventListener);

    void setUp(List<PlayerIdStack> playersStacks,
               String smallBlindId,
               String bigBlindId,
               int smallBlindSize);

    void call() throws Exception;

    void raise(int additionalWager) throws Exception;

    void allIn() throws Exception;

    void fold() throws Exception;

    void check() throws Exception;

    void showDown() throws Exception;
}
