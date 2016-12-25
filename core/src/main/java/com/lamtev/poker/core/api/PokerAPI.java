package com.lamtev.poker.core.api;

import com.lamtev.poker.core.model.Cards;

import java.util.List;

//TODO javadoc
public interface PokerAPI {
    void addStateChangedListener(StateChangedListener stateChangedListener);

    void addGameIsOverListener(GameIsOverListener gameIsOverListener);

    void addCurrentPlayerIdListener(CurrentPlayerListener currentPlayerListener);

    void addCommunityCardsListener(CommunityCardsListener communityCardsListener);

    void addWagerPlacedListener(WagerPlacedListener wagerPlacedListener);

    void addPlayerFoldListener(PlayerFoldListener playerFoldListener);

    void addMoveAbilityListener(MoveAbilityListener moveAbilityListener);

    void addPreflopMadeListener(PreflopMadeListener preflopMadeListener);

    void addPlayerShowedDownListener(PlayerShowedDownListener playerShowedDownListener);

    void setUp(List<PlayerInfo> playersInfo, int smallBlindSize);

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

    void showDown() throws Exception;
}
