package com.lamtev.poker.core.api;

import java.util.List;

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

    void call() throws Exception;

    void raise(int additionalWager) throws Exception;

    void allIn() throws Exception;

    void fold() throws Exception;

    void check() throws Exception;

    void showDown() throws Exception;
}
