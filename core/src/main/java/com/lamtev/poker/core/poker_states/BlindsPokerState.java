package com.lamtev.poker.core.poker_states;

import com.lamtev.poker.core.Bank;
import com.lamtev.poker.core.Players;

public class BlindsPokerState implements PokerState {

    private Players players;
    private Bank bank;
    private int smallBlindSize;

    public BlindsPokerState(Players players, Bank bank, int smallBlindSize) {
        this.players = players;
        this.bank = bank;
        this.smallBlindSize = smallBlindSize;
    }

    @Override
    public void nextState(OnNextStateListener onNextStateListener) {
        onNextStateListener.nextState();
    }

    @Override
    public void setBlinds() {
        bank.acceptBlindWagers(smallBlindSize);
    }

    @Override
    public void call() {}

    @Override
    public void raise(int additionalWager) {}

    @Override
    public void fold() {}

    @Override
    public void check() {}

}
