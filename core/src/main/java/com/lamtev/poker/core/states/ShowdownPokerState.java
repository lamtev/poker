package com.lamtev.poker.core.states;

import com.lamtev.poker.core.model.Cards;

//TODO
class ShowdownPokerState extends ActionPokerState {

    ShowdownPokerState(ActionPokerState state) {
        super(state);
    }

    @Override
    public void call() throws Exception {

    }

    @Override
    public void raise(int additionalWager) throws Exception {

    }

    @Override
    public void allIn() throws Exception {

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
