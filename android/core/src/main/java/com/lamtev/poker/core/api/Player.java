package com.lamtev.poker.core.api;

import com.lamtev.poker.core.model.Card;

import java.util.List;

public interface Player {

    String id();

    int stack();

    int wager();

    List<Card> cards();

    boolean isActive();

}
