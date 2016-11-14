package com.lamtev.poker.core.states;

import com.lamtev.poker.core.Player;
import com.lamtev.poker.core.Poker;

class PreflopWageringPokerState extends WageringState {

    private Poker poker;

    PreflopWageringPokerState(Poker poker) {
        super(poker);
        this.poker = poker;
        poker.getDealer().makePreflop();
    }

    @Override
    public void setBlinds() {
        super.setBlinds();
        if (isTimeToNextState()) {
            nextState();
        }
    }

    @Override
    public void call() throws Exception {
        super.call();
        if (isTimeToNextState()) {
            nextState();
        }
    }

    @Override
    public void fold() {
        super.fold();
        //TODO goToFinalState when 1 active player
        if (isTimeToNextState()) {
            nextState();
        }
    }

    @Override
    public void check() throws Exception {
        super.check();
        if (isTimeToNextState()) {
            nextState();
        }
    }

    private boolean isTimeToNextState() {
        //TODO isTimeToNextState
        return arePlayersHaveSameWagers();// && other conditions;
    }

    private boolean arePlayersHaveSameWagers() {
        for (Player player : poker.getPlayers()) {
            if (player.getWager() != poker.getBank().getCurrentWager()) {
                return false;
            }
        }
        return true;
    }

    private void nextState() {
        poker.update(() -> poker.setState(new FlopWageringPokerState(poker)));
    }

}
