package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.Bank;

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
    public void setBlinds() throws Exception {
        bank.acceptBlindWagers(smallBlindSize);
        nextState();
    }

    @Override
    public void call() {
    }

    @Override
    public void raise(int additionalWager) {
    }

    @Override
    public void fold() throws Exception {
    }

    @Override
    public void check() {
    }

    private void nextState() {
        poker.update(() -> poker.setState(new PreflopWageringPokerState(poker)));
    }

}
