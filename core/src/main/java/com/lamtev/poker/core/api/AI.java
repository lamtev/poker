package com.lamtev.poker.core.api;

import com.lamtev.poker.core.event_listeners.*;
import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;

import java.util.List;
import java.util.Map;

public interface AI extends
        Player,
        BankMoneyUpdatedListener,
        BlindWagersPlacedListener,
        CommunityCardsDealtListener,
        CurrentPlayerChangedListener,
        HoleCardsDealtListener,
        MoveAbilityListener,
        PlayerAllinnedListener,
        PlayerCalledListener,
        PlayerCheckedListener,
        PlayerFoldListener,
        PlayerMoneyUpdatedListener,
        PlayerRaisedListener,
        PlayerShowedDownListener,
        RoundOfPlayChangedListener,
        RoundOfPlayIsOverListener,
        StateChangedListener {

    void makeAMove();

    @Override
    @Deprecated
    default void playerShowedDown(String playerId, PokerHand hand) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default void holeCardsDealt(Map<String, List<Card>> playerIdToCards) {
        throw new UnsupportedOperationException();
    }

}
