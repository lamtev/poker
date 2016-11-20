package com.lamtev.poker.core.states;

import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.util.PlayerInfo;

import java.util.ArrayList;
import java.util.List;

public interface PokerState {

    void setUp(List<PlayerInfo> playersInfo, int smallBlindSize) throws Exception;

    int getPlayerWager(String playerID) throws Exception;

    int getPlayerStack(String playerID) throws Exception;

    int getMoneyInBank() throws Exception;

    Cards getCommonCards() throws Exception;

    ArrayList<PlayerInfo> getPlayersInfo() throws Exception;

    void call() throws Exception;

    void raise(int additionalWager) throws Exception;

    void allIn() throws Exception;

    void fold() throws Exception;

    void check() throws Exception;

    Cards showDown() throws Exception;
}
