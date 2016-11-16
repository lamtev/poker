package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.*;

abstract class SettingsFinishedPokerState implements PokerState {

    private Poker poker;
    private Players players;
    private Bank bank;
    private Dealer dealer;
    private Cards commonCards;

    SettingsFinishedPokerState(Poker poker, Players players, Bank bank, Dealer dealer, Cards commonCards) {
        this.poker = poker;
        this.players = players;
        this.bank = bank;
        this.dealer = dealer;
        this.commonCards = commonCards;
    }

    @Override
    public int getPlayerWager(String playerID) throws Exception {
        return players.get(playerID).getWager();
    }

    @Override
    public int getPlayerStack(String playerID) throws Exception {
        return players.get(playerID).getStack();
    }

    @Override
    public int getMoneyInBank() throws Exception {
        return bank.getMoney();
    }

    @Override
    public Cards getCommonCards() throws Exception {
        return commonCards;
    }

    void setState(Class<? extends SettingsFinishedPokerState> className) throws Exception {
        poker.setState(
                className
                        .getConstructor(Poker.class, Players.class, Bank.class, Dealer.class, Cards.class)
                        .newInstance(poker, players, bank, dealer, commonCards)
        );
    }

}
