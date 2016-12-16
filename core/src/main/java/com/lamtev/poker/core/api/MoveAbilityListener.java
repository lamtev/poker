package com.lamtev.poker.core.api;

//TODO javadoc
public interface MoveAbilityListener {
    MoveAbilityListener setCallAbility(boolean flag);

    MoveAbilityListener setRaiseAbility(boolean flag);

    MoveAbilityListener setAllInAbility(boolean flag);

    MoveAbilityListener setCheckAbility(boolean flag);

    MoveAbilityListener setFoldAbility(boolean flag);

    MoveAbilityListener setShowDownAbility(boolean flag);
}
