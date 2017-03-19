package com.lamtev.poker.core.event_listeners;

import com.lamtev.poker.core.model.Card;

import java.util.List;

public interface CommunityCardsListener {
    void communityCardsAdded(List<Card> addedCommunityCards);
}
