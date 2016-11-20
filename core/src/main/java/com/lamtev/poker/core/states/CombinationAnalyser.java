package com.lamtev.poker.core.states;

import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.model.Players;

//TODO combination analyser
public class CombinationAnalyser {

    private Players players;
    private Cards commonCards;

    public CombinationAnalyser(Players players, Cards commonCards) {
        this.players = players;
        this.commonCards = commonCards;
    }

    public Players determineWinners() {
        return new Players();
    }

}
