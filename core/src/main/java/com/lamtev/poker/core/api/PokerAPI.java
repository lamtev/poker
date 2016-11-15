package com.lamtev.poker.core.api;

import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.util.PlayerInfo;

import java.util.ArrayList;

public interface PokerAPI {
    void start(ArrayList<PlayerInfo> playersInfo, int smallBlindSize) throws Exception;

    int getPlayerWager(String playerID);

    int getPlayerStack(String playerID);

    int getMoneyInBank();

    Cards getPlayerCards(String playerID);

    Cards getCommonCards();

    ArrayList<PlayerInfo> getPlayersInfo();

    void call() throws Exception;

    void raise(int additionalWager) throws Exception;

    void fold() throws Exception;

    void check() throws Exception;
}
