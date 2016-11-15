package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.Bank;
import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.model.Players;
import com.lamtev.poker.core.states.exceptions.GameIsOverException;

class WinnersDeterminingPokerState implements PokerState {

    private Poker poker;
    private Players players;
    private Cards commonCards;
    private Bank bank;

    WinnersDeterminingPokerState(Poker poker) {
        this.poker = poker;
        this.players = poker.getPlayers();
        this.commonCards = poker.getCommonCards();
        this.bank = poker.getBank();
    }

    @Override
    public void setBlinds() throws Exception {
        throw new GameIsOverException();
    }

    @Override
    public void call() throws Exception {
        throw new GameIsOverException();
    }

    @Override
    public void raise(int additionalWager) throws Exception {
        throw new GameIsOverException();
    }

    @Override
    public void fold() throws Exception {
        throw new GameIsOverException();
    }

    @Override
    public void check() throws Exception {
        throw new GameIsOverException();
    }

}
