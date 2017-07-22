package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.PlayerIdStack;
import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.Players;
import com.lamtev.poker.core.states.exceptions.RoundOfPlayIsOverException;

import java.util.ArrayList;
import java.util.List;

class RoundOfPlayIsOverState extends AbstractPokerState {

    RoundOfPlayIsOverState(Poker poker, Players players) {
        poker.notifyRoundOfPlayIsOverListeners(new ArrayList<PlayerIdStack>() {{
            players.forEach(player -> add(new PlayerIdStack(player.id(), player.stack())));
        }});
    }

    @Override
    public void setUp(List<PlayerIdStack> playersInfo, String dealerId, int smallBlindSize)
            throws RoundOfPlayIsOverException {
        throw new RoundOfPlayIsOverException();
    }

    @Override
    public void placeBlindWagers() throws RoundOfPlayIsOverException {
        throw new RoundOfPlayIsOverException();
    }

    @Override
    public void call() throws RoundOfPlayIsOverException {
        throw new RoundOfPlayIsOverException();
    }

    @Override
    public void raise(int additionalWager) throws RoundOfPlayIsOverException {
        throw new RoundOfPlayIsOverException();
    }

    @Override
    public void allIn() throws RoundOfPlayIsOverException {
        throw new RoundOfPlayIsOverException();
    }

    @Override
    public void fold() throws RoundOfPlayIsOverException {
        throw new RoundOfPlayIsOverException();
    }

    @Override
    public void check() throws RoundOfPlayIsOverException {
        throw new RoundOfPlayIsOverException();
    }

    @Override
    public void showDown() throws RoundOfPlayIsOverException {
        throw new RoundOfPlayIsOverException();
    }

}
