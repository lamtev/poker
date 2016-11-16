package com.lamtev.poker.core.api;

import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.util.PlayerInfo;

import java.util.ArrayList;

public interface PokerAPI {
    int getPlayerWager(String playerID) throws Exception;

    int getPlayerStack(String playerID) throws Exception;

    int getMoneyInBank() throws Exception;

    Cards getPlayerCards(String playerID) throws Exception;

    Cards getCommonCards() throws Exception;

    ArrayList<PlayerInfo> getPlayersInfo() throws Exception;

    void call() throws Exception;

    void raise(int additionalWager) throws Exception;

    void fold() throws Exception;

    void check() throws Exception;
}
