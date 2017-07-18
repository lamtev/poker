package com.lamtev.poker.core.api;

import com.lamtev.poker.core.event_listeners.*;

public interface PokerAI extends CommunityCardsDealtListener,
        HoleCardsDealtListener,
        MoneyChangedListener,
        CurrentPlayerChangedListener,
        MoveAbilityListener,
        StateChangedListener,
        PlayerFoldListener,
        PlayerShowedDownListener {

    String id();

}
