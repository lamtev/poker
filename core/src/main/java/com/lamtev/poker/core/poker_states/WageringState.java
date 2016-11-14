package com.lamtev.poker.core.poker_states;

import com.lamtev.poker.core.Bank;
import com.lamtev.poker.core.Players;

public abstract class WageringState implements PokerState {

    private Players players;
    private Bank bank;

    public WageringState(Players players, Bank bank) {
        this.players = players;
        this.bank = bank;
    }

    @Override
    public void nextState(OnNextStateListener onNextStateListener) {
        onNextStateListener.nextState();
    }

    @Override
    public void setBlinds() {}

    @Override
    public void call() {

    }

    @Override
    public void raise(int additionalWager) {

    }

    @Override
    public void fold() {

    }

    @Override
    public void check() {

    }
}
