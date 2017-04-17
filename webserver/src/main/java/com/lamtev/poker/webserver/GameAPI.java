package com.lamtev.poker.webserver;

import com.lamtev.poker.core.api.PlayerExpandedInfo;
import com.lamtev.poker.core.model.Card;

import java.util.List;
import java.util.Map;

public interface GameAPI {
    void start(String humanId, int playersNumber, int stack) throws Exception;

    Map<String, PlayerExpandedInfo> getPlayersInfo();

    int getBank();

    String getCurrentPlayerId();

    String getCurrentStateName();

    List<Card> getCommunityCards();

    List<Card> getPlayerCards(String playerId);

    void call() throws Exception;

    void raise(int additionalWager) throws Exception;

    void allIn() throws Exception;

    void fold() throws Exception;

    void check() throws Exception;

    void showDown() throws Exception;
}
