package com.lamtev.poker.core.event_listeners;

import com.lamtev.poker.core.ai.AbstractAI.Rival;

import java.util.List;

public interface RivalsBecomeKnownListener {

    String id();

    void rivalsBecomeKnown(List<Rival> rivals);

}
