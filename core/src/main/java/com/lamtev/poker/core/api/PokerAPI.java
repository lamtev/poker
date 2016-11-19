package com.lamtev.poker.core.api;

import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.states.WageringEndListener;
import com.lamtev.poker.core.util.PlayerInfo;

import java.util.List;

public interface PokerAPI {
    void addWageringEndListener(WageringEndListener wageringEndListener) throws Exception;

    void setUp(List<PlayerInfo> playersInfo, int smallBlindSize) throws Exception;

    int getPlayerWager(String playerID) throws Exception;

    int getPlayerStack(String playerID) throws Exception;

    int getMoneyInBank() throws Exception;

    Cards getPlayerCards(String playerID) throws Exception;

    Cards getCommonCards() throws Exception;

    List<PlayerInfo> getPlayersInfo() throws Exception;

    void call() throws Exception;

    void raise(int additionalWager) throws Exception;

    void fold() throws Exception;

    void check() throws Exception;

    Cards showDown() throws Exception;
}
