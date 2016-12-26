package com.lamtev.poker.desktop;

import com.lamtev.poker.core.api.CurrentPlayerListener;
import com.lamtev.poker.core.api.StateChangedListener;

public class AI implements CurrentPlayerListener, StateChangedListener {

    private final String id;
    private PokerGame pokerGame;
    private String state;
    private String currentPlayerId;

    public AI(String id) {
        this.id = id;
    }

    public void setPokerGame(PokerGame pokerGame) {
        this.pokerGame = pokerGame;
    }

    @Override
    public void stateChanged(String stateName) {
        state = stateName;
        doAction(currentPlayerId);
    }

    @Override
    public void currentPlayerChanged(String playerId) {
        currentPlayerId = playerId;
        doAction(playerId);
    }

    private void doAction(String playerId) {
//        if (playerId != null && playerId.equals(id)) {
//            switch (state) {
//                case "PreflopWageringPokerState":
//                case "FlopWageringPokerState":
//                case "TurnWageringPokerState":
//                case "RiverWageringPokerState":
//                    System.out.println(state + " " + id + " <====> " + currentPlayerId);
//                    pokerGame.doFold();
//                    break;
//                case "ShowDownPokerState":
//                    pokerGame.doShowDown();
//                    break;
//            }
//        }
    }

}
