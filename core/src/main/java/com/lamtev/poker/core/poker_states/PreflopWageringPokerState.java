package com.lamtev.poker.core.poker_states;

import com.lamtev.poker.core.Bank;
import com.lamtev.poker.core.Dealer;
import com.lamtev.poker.core.Players;

public class PreflopWageringPokerState extends WageringState {

    public PreflopWageringPokerState(Players players, Bank bank, Dealer dealer) {
        super(players, bank);
        dealer.makePreflop();
    }

}
