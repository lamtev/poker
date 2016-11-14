package com.lamtev.poker.core.poker_states;

import com.lamtev.poker.core.poker_states.OnNextStateListener;

public interface PokerState {
    void nextState(OnNextStateListener onNextStateListener);
    void setBlinds();
    void call() throws Exception;
    void raise(int additionalWager) throws Exception;
    void fold();
    void check() throws Exception;
}
