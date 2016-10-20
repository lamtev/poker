package com.lamtev.poker.core;

public enum GameStage {

    BLINDS,
    PREFLOP,
    FIRST_WAGERING_LAP,
    FLOP,
    SECOND_WAGERING_LAP,
    TURN,
    THIRD_WAGERING_LAP,
    RIVER,
    FOURTH_WAGERING_LAP;

    public GameStage next() {
        int i = -1;
        for (GameStage gs : values()) {
            ++i;
            if (gs.equals(this)) {
                return values()[(i + 1) % values().length];
            }
        }
        return null;
    }
}
