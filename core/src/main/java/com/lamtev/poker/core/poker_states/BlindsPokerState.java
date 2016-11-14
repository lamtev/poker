package com.lamtev.poker.core.poker_states;

import com.lamtev.poker.core.*;

public class BlindsPokerState implements PokerState {

    private Poker poker;
    private Bank bank;
    private int smallBlindSize;

    public BlindsPokerState(Poker poker, int smallBlindSize) {
        this.poker = poker;
        this.bank = poker.getBank();
        this.smallBlindSize = smallBlindSize;
    }

    @Override
    public void setBlinds() {
        bank.acceptBlindWagers(smallBlindSize);
        nextState();
    }

    @Override
    public void call() {}

    @Override
    public void raise(int additionalWager) {}

    @Override
    public void fold() {}

    @Override
    public void check() {}

    private void nextState() {
        poker.update((state) -> state = new PreflopWageringPokerState(poker));
    }

}
