package com.lamtev.poker.core.states;

abstract class AbstractPokerState implements PokerState {
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
