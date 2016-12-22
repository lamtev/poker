package com.lamtev.poker.desktop;

import com.lamtev.poker.core.api.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class TexasHoldemPoker implements StateChangedListener, GameIsOverListener {

    private List<PlayerInfo> playersInfo;
    private String state;
    private Stage primaryStage;

    public TexasHoldemPoker(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void init(int numberOfCompetitors) {
        playersInfo = new ArrayList<>();
        int stack = 10000;
        playersInfo.add(new PlayerInfo("PLAYER", stack));
        for (int i = 0; i < numberOfCompetitors - 1; ++i) {
            playersInfo.add(new PlayerInfo("BOT" + (i + 1), stack));
        }
    }

    public GridPane getGridPane(int numberOfCompetitors) {
        PokerAPI poker = new Poker();
        init(numberOfCompetitors);
        GridPane gp = new GridPane();
        Label label = new Label();
        gp.add(label, 0, 0);
        try {
            poker.addStateChangedListener(this);
            poker.addGameIsOverListener(this);
            poker.setUp(playersInfo, 50);
            switch (state) {
                case "PreflopWageringPokerState":
                case "FlopWageringPokerState":
                case "TurnWageringPokerState":
                case "RiverWageringPokerState":
                    Button call = new Button("call");
                    call.setOnAction(event -> {
                        try {
                            poker.call();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    gp.add(call, 5, 0);

                    Button fold = new Button("fold");
                    fold.setOnAction(event -> {
                        try {
                            poker.fold();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    gp.add(fold, 5, 1);

                    Button check = new Button("check");
                    check.setOnAction(event -> {
                        try {
                            poker.check();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    gp.add(check, 5, 2);

                    Button allIn = new Button("allIn");
                    allIn.setOnAction(event -> {
                        try {
                            poker.allIn();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    gp.add(allIn, 5, 3);

                    Button raise = new Button("raise");

                    gp.add(raise, 5, 4);

                    break;
                case "ShowDownPokerState":
                    Button showDown = new Button("showDown");
                    showDown.setOnAction(event -> {
                        try {
                            String name = poker.showDown().getDeclaringClass().getSimpleName();
                            label.setText(name);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    gp.add(showDown, 5, 5);
                    break;
                case "GameIsOverPokerState":
                    //may be recursive call of poker creation ???
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(gp, 1200, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
        System.out.println(numberOfCompetitors);
        return gp;
    }

    @Override
    public void changeState(String stateName) {
        state = stateName;
    }

    @Override
    public void updatePlayersInfo(List<PlayerInfo> playersInfo) {
        this.playersInfo = playersInfo;
    }
}
