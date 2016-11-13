package com.lamtev.poker.core;

public class PreflopWageringPokerState extends WageringState {

    public PreflopWageringPokerState(Poker poker) {
        super(poker);
        poker.dealer().makePreflop();
    }

}
