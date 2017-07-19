package com.lamtev.poker.core.api;

import com.lamtev.poker.core.event_listeners.*;
import com.lamtev.poker.core.model.Card;

import java.util.List;

public interface PokerPlay extends
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
        RoundOfPlayIsOverListener,
        StateChangedListener {

    @Override
    default void holeCardsDealt(List<Card> holeCards) {
        throw new UnsupportedOperationException();
    }
}