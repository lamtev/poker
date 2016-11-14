package com.lamtev.poker.core.states;

import com.lamtev.poker.core.Poker;

class FlopWageringPokerState extends WageringState {

    FlopWageringPokerState(Poker poker) {
        super(poker);
        poker.getDealer().makeFlop();
    }

}
