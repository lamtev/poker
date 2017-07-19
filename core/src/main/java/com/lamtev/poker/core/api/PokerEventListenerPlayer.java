package com.lamtev.poker.core.api;

import com.lamtev.poker.core.event_listeners.*;
import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;

import java.util.List;

@Deprecated
public interface PokerEventListenerPlayer extends
        CommunityCardsDealtListener,
        CurrentPlayerChangedListener,
        RoundOfPlayIsOverListener,
        MoveAbilityListener,
        PlayerFoldListener,
        PlayerShowedDownListener,
        StateChangedListener,
        PlayerMoneyUpdatedListener {
    @Override
    void playerFold(String playerId);

    @Override
    void playerMoneyUpdated(String playerId, int playerStack, int playerWager);

    @Override
    void stateChanged(String stateName);

    @Override
    void currentPlayerChanged(String currentPlayerId);

    @Override
    void callAbilityChanged(boolean flag);

    @Override
    void roundOfPlayIsOver(List<PlayerIdStack> playersInfo);

    @Override
    void playerShowedDown(String playerId, List<Card> holeCards, PokerHand hand);

    @Override
    void raiseAbilityChanged(boolean flag);

    @Override
    void communityCardsDealt(List<Card> addedCommunityCards);

    @Override
    void allInAbilityChanged(boolean flag);

    @Override
    void checkAbilityChanged(boolean flag);

    @Override
    void foldAbilityChanged(boolean flag);

    @Override
    void showDownAbilityChanged(boolean flag);
}
