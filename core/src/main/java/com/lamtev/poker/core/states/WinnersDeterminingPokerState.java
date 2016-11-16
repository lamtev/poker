package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.Bank;
import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.model.Dealer;
import com.lamtev.poker.core.model.Players;
import com.lamtev.poker.core.states.exceptions.GameIsOverException;
import com.lamtev.poker.core.util.PlayerInfo;

import java.util.ArrayList;

class WinnersDeterminingPokerState extends SettingsFinishedPokerState {

    public WinnersDeterminingPokerState(Poker poker, Players players, Bank bank, Dealer dealer, Cards commonCards) {
        super(poker, players, bank, dealer, commonCards);
    }

    @Override
    public void setBlinds(int smallBlindSize) throws Exception {
        throw new GameIsOverException();
    }

    @Override
    public Cards getPlayerCards(String playerID) throws Exception {
        return players.get(playerID).getCards();
    }

    @Override
    public ArrayList<PlayerInfo> getPlayersInfo() throws Exception {
        return new ArrayList<PlayerInfo>() {{
            players.forEach((player) -> {
                String id = player.getId();
                int stack = player.getStack();
                add(new PlayerInfo(id, stack));
            });
        }};
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
