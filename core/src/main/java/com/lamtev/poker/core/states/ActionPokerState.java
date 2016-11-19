package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.Bank;
import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.model.Dealer;
import com.lamtev.poker.core.model.Players;
import com.lamtev.poker.core.util.PlayerInfo;

import java.util.ArrayList;
import java.util.List;

abstract class ActionPokerState implements PokerState {

    protected Poker poker;
    protected Players players;
    protected Bank bank;
    protected Dealer dealer;
    protected Cards commonCards;

    ActionPokerState(Poker poker, Players players, Bank bank, Dealer dealer, Cards commonCards) {
        this.poker = poker;
        this.players = players;
        this.bank = bank;
        this.dealer = dealer;
        this.commonCards = commonCards;
    }

    @Override
    public void addWageringEndListener(WageringEndListener wageringEndListener) throws Exception {
        throw new Exception();
    }

    @Override
    public void setUp(List<PlayerInfo> playersInfo, int smallBlindSize) throws Exception {
        throw new Exception();
    }

    @Override
    public Cards getPlayerCards(String playerID) throws Exception {
        throw new Exception();
    }

    @Override
    public ArrayList<PlayerInfo> getPlayersInfo() throws Exception {
        return null;
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
