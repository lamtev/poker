package com.lamtev.poker.core.poker_states;

import com.lamtev.poker.core.poker_states.OnNextStateListener;

public interface PokerState {
    void nextState(OnNextStateListener onNextStateListener);
    void setBlinds();
    void call();
    void raise(int additionalWager);
    void fold();
    void check();
}
