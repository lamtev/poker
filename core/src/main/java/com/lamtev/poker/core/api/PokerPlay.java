package com.lamtev.poker.core.api;

import com.lamtev.poker.core.event_listeners.*;

public interface PokerPlay extends CommunityCardsDealtListener,
        CurrentPlayerChangedListener,
        MoneyChangedListener,
        MoveAbilityListener,
        PlayerFoldListener,
        PlayerShowedDownListener,
        RoundOfPlayIsOverListener,
        StateChangedListener {
}
