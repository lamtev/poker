package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.PlayerInfo;
import com.lamtev.poker.core.hands.PokerHand;
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
    public void call() throws Exception {
        throw new GameIsOverException();
    }

    @Override
    public void raise(int additionalWager) throws Exception {
        throw new GameIsOverException();
    }

    @Override
    public void allIn() throws Exception {
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

    @Override
    public PokerHand.Name showDown() throws Exception {
        throw new GameIsOverException();
    }
}
