package com.lamtev.poker.core.api;

import com.lamtev.poker.core.model.Card;

import java.util.List;

public interface CommunityCardsListener {
    void communityCardsAdded(List<Card> addedCommunityCards);
}
