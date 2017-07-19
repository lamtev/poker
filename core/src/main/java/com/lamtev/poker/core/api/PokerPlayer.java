package com.lamtev.poker.core.api;

import com.lamtev.poker.core.model.Card;

import java.util.List;

public interface PokerPlayer {

    String id();

    int stack();

    int wager();

    List<Card> cards();

    boolean isActive();

}
