package com.lamtev.poker.core.event_listeners;

//TODO javadoc
public interface MoveAbilityListener {
    void allInAbilityChanged(boolean flag);

    void callAbilityChanged(boolean flag);

    void checkAbilityChanged(boolean flag);

    void foldAbilityChanged(boolean flag);

    void raiseAbilityChanged(boolean flag);

    void showDownAbilityChanged(boolean flag);

    String id();

}
