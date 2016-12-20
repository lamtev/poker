package com.lamtev.poker.core.api;

import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Cards;

import java.util.List;

//TODO javadoc
public interface PokerAPI {
    void addStateChangedListener(StateChangedListener stateChangedListener) throws Exception;

    void setUp(List<PlayerInfo> playersInfo, int smallBlindSize) throws Exception;

    int getPlayerWager(String playerID) throws Exception;

    Cards getPlayerCards(String playerID) throws Exception;

    int getPlayerStack(String playerID) throws Exception;

    int getMoneyInBank() throws Exception;

    Cards getCommonCards() throws Exception;

    List<PlayerInfo> getPlayersInfo() throws Exception;

    void call() throws Exception;

    void raise(int additionalWager) throws Exception;

    void allIn() throws Exception;

    void fold() throws Exception;

    void check() throws Exception;

    PokerHand.Name showDown() throws Exception;
}
