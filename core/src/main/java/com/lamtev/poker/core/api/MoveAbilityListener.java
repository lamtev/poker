package com.lamtev.poker.core.api;

//TODO javadoc
public interface MoveAbilityListener {
    void callAbilityChanged(boolean flag);

    void raiseAbilityChanged(boolean flag);

    void allInAbilityChanged(boolean flag);

    void checkAbilityChanged(boolean flag);

    void foldAbilityChanged(boolean flag);

    void showDownAbilityChanged(boolean flag);
}
