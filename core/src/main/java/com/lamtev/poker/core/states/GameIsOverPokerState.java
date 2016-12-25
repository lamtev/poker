package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.PlayerInfo;
import com.lamtev.poker.core.states.exceptions.GameIsOverException;

import java.util.ArrayList;
import java.util.List;

class GameIsOverPokerState extends ActionPokerState {

    GameIsOverPokerState(ActionPokerState state) {
        super(state.poker, state.players, state.bank, state.dealer, state.commonCards);
        poker.notifyGameIsOverListeners(new ArrayList<PlayerInfo>() {{
            players.forEach(player -> add(new PlayerInfo(player.getId(), player.getStack())));
        }});
    }

    @Override
    public void setUp(List<PlayerInfo> playersInfo, int smallBlindSize) {
        throw new GameIsOverException();
    }

    @Override
    public void call() throws GameIsOverException {
        throw new GameIsOverException();
    }

    @Override
    public void raise(int additionalWager) throws GameIsOverException {
        throw new GameIsOverException();
    }

    @Override
    public void allIn() throws GameIsOverException {
        throw new GameIsOverException();
    }

    @Override
    public void fold() throws GameIsOverException {
        throw new GameIsOverException();
    }

    @Override
    public void check() throws Exception {
        throw new GameIsOverException();
    }

    @Override
    public void showDown() throws Exception {
        throw new GameIsOverException();
    }
}
