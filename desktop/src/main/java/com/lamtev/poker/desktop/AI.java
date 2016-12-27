package com.lamtev.poker.desktop;

import com.lamtev.poker.core.api.CurrentPlayerListener;
import com.lamtev.poker.core.api.StateChangedListener;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AI implements CurrentPlayerListener, StateChangedListener {

    private static final Random RANDOM = new Random(System.currentTimeMillis());
    private List<Runnable> actions;

    private final String id;
    private PokerGame pokerGame;
    private String state;
    private String currentPlayerId;

    public AI(String id) {
        this.id = id;
    }

    public void setPokerGame(PokerGame pokerGame) {
        this.pokerGame = pokerGame;
        actions = new ArrayList<Runnable>() {{
            add(pokerGame::doCheck);
            add(pokerGame::doFold);
            add(pokerGame::doCall);
            add(pokerGame::doAllIn);
        }};
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
        if (playerId != null && playerId.equals(id)) {


            try {
                Timeline timeline = new Timeline(new KeyFrame(
                        Duration.millis(5000),
                        ae -> {
                            switch (state) {
                                case "PreflopWageringPokerState":
                                case "FlopWageringPokerState":
                                case "TurnWageringPokerState":
                                case "RiverWageringPokerState":
                                    System.out.println(state + " " + id + " <====> " + currentPlayerId);
                                    actions.get(RANDOM.nextInt(actions.size())).run();
                                    break;
                                case "ShowdownPokerState":
                                    pokerGame.doShowDown();
                                    break;
                            }
                        })
                );
                timeline.play();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
