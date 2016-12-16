package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.PlayerInfo;
import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.states.exceptions.GameIsOverException;

import java.util.ArrayList;
import java.util.List;

class GameIsOverPokerState extends ActionPokerState {

    GameIsOverPokerState(ActionPokerState state) {
        super(state.poker, state.players, state.bank, state.dealer, state.commonCards);
    }

    @Override
    public void setUp(List<PlayerInfo> playersInfo, int smallBlindSize) throws Exception {
        throw new GameIsOverException();
    }

    @Override
    //TODO: Разве нельзя получать информацию об состоянии игроков во время игры?
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
    public Cards showDown() throws Exception {
        throw new GameIsOverException();
    }
}
