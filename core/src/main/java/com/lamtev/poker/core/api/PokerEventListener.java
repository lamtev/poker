package com.lamtev.poker.core.api;

import com.lamtev.poker.core.event_listeners.*;
import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Cards;

import java.util.List;
import java.util.Map;

public interface PokerEventListener extends
        //TODO may be all listeners should be inlined into this
        CommunityCardsDealtListener,
        CurrentPlayerChangedListener,
        RoundOfPlayIsOverListener,
        MoveAbilityListener,
        PlayerFoldListener,
        PlayerShowedDownListener,
        PreflopMadeListener,
        StateChangedListener,
        MoneyChangedListener {
    @Override
    void playerFold(String foldPlayerId);

    @Override
    void onMoneyChanged(String playerId, int playerStack, int playerWager, int bank);

    @Override
    void stateChanged(String stateName);

    @Override
    void onCurrentPlayerChanged(String currentPlayerId);

    @Override
    void callAbilityChanged(boolean flag);

    @Override
    void onRoundOfPlayIsOver(List<PlayerIdStack> playersInfo);

    @Override
    void playerShowedDown(String playerId, List<Card> holeCards, PokerHand hand);

    @Override
    void raiseAbilityChanged(boolean flag);

    @Override
    void preflopMade(Map<String, Cards> playerIdToCards);

    @Override
    void onCommunityCardsDealt(List<Card> addedCommunityCards);

    @Override
    void allInAbilityChanged(boolean flag);

    @Override
    void checkAbilityChanged(boolean flag);

    @Override
    void foldAbilityChanged(boolean flag);

    @Override
    void showDownAbilityChanged(boolean flag);
}
