package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.PlayerIdStack;
import com.lamtev.poker.core.states.exceptions.*;

import java.util.ArrayList;
import java.util.List;

class GameIsOverPokerState extends ActionPokerState {

    GameIsOverPokerState(ActionPokerState state) {
        super(state.poker(), state.players(), state.bank(), state.dealer(), state.communityCards());
        poker().notifyGameOverListeners(new ArrayList<PlayerIdStack>() {{
            players().forEach(player -> add(new PlayerIdStack(player.getId(), player.getStack())));
        }});
    }

    @Override
    public void setUp(List<PlayerIdStack> playersInfo, String dealerId, int smallBlindSize) throws GameOverException {
        throw new GameOverException();
    }

    @Override
    public void placeBlindWagers() throws GameOverException {
        throw new GameOverException();
    }

    @Override
    public void call() throws GameOverException {
        throw new GameOverException();
    }

    @Override
    public void raise(int additionalWager) throws GameOverException {
        throw new GameOverException();
    }

    @Override
    public void allIn() throws GameOverException {
        throw new GameOverException();
    }

    @Override
    public void fold() throws GameOverException {
        throw new GameOverException();
    }

    @Override
    public void check() throws GameOverException {
        throw new GameOverException();
    }

    @Override
    public void showDown() throws GameOverException {
        throw new GameOverException();
    }

}
