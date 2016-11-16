package com.lamtev.poker.core.states;

import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.util.PlayerInfo;

import java.util.ArrayList;

public interface PokerState {
    default void setBlinds(int smallBlindSize) throws Exception {
        throw new Exception();
    }

    int getPlayerWager(String playerID) throws Exception;

    int getPlayerStack(String playerID) throws Exception;

    int getMoneyInBank() throws Exception;

    default Cards getPlayerCards(String playerID) throws Exception {
        throw new Exception();
    }

    Cards getCommonCards() throws Exception;

    default ArrayList<PlayerInfo> getPlayersInfo() throws  Exception {
        throw new Exception();
    }

    void call() throws Exception;

    void raise(int additionalWager) throws Exception;

    void fold() throws Exception;

    void check() throws Exception;
}
