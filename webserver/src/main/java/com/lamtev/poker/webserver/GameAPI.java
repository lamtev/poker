package com.lamtev.poker.webserver;

import com.lamtev.poker.core.api.PlayerExpandedInfo;

import java.util.Map;

public interface GameAPI {
    void start(String humanId, int playersNumber, int stack);

    Map<String, PlayerExpandedInfo> getPlayersInfo();

    int getBank();

    String getCurrentPlayerId();

    String getCurrentStateName();

    void call() throws Exception;

    void raise(int additionalWager) throws Exception;

    void allIn() throws Exception;

    void fold() throws Exception;

    void check() throws Exception;

    void showDown() throws Exception;
}
