package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.Bank;
import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.model.Dealer;
import com.lamtev.poker.core.model.Players;

abstract class SettingsFinishedPokerState implements PokerState {

    protected Poker poker;
    protected Players players;
    protected Bank bank;
    protected Dealer dealer;
    protected Cards commonCards;

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

}
