package com.lamtev.poker.core.states;

import com.lamtev.poker.core.model.Cards;

public class ShowdownPokerState extends ActionPokerState {

    ShowdownPokerState(ActionPokerState state) {
        super(state.poker, state.players, state.bank, state.dealer, state.commonCards);
    }

    @Override
    public void call() throws Exception {

    }

    @Override
    public void raise(int additionalWager) throws Exception {

    }

    @Override
    public void fold() throws Exception {

    }

    @Override
    public void check() throws Exception {

    }

    @Override
    public Cards showDown() throws Exception {
        return null;
    }
}
