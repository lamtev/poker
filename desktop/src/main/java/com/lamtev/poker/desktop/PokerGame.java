package com.lamtev.poker.desktop;

import com.lamtev.poker.core.api.*;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class PokerGame implements StateChangedListener, GameIsOverListener {

    private List<PlayerInfo> playersInfo;
    private PokerAPI poker = new Poker();
    private String stateName;
    private int smallBlindSize;

    private GridPane root = new GridPane();
    private HBox sb = new HBox();
    private Label statusBar = new Label("nothing happen");
    private Label bank = new Label();
    private Label nickName = new Label();
    private ListView<Label> activePlayersList = new ListView<>();
    private ListView<Label> foldPlayersList = new ListView<>();
    private Button call = new Button("call");
    private Button fold = new Button("fold");
    private Button raise = new Button("raise");
    private Button check = new Button("check");
    private Button allIn = new Button("all in");
    private Button showDown = new Button("show down");
    private Separator verticalSeparator = new Separator(Orientation.VERTICAL);
    private Separator horizontalSeparator = new Separator(Orientation.HORIZONTAL);
    private List<ImageView> communityCardsView = new ArrayList<>();

    public PokerGame(List<PlayerInfo> playersInfo) {
        this.playersInfo = playersInfo;
        poker.addStateChangedListener(this);
        poker.addGameIsOverListener(this);
        smallBlindSize = playersInfo.get(0).getStack() / 100;
        poker.setUp(playersInfo, smallBlindSize);
        playersInfo.forEach(playerInfo -> {
            activePlayersList.getItems().add(new Label(playerInfo.getId() + ": " + playerInfo.getStack()));
            foldPlayersList.getItems().add(new Label(playerInfo.getId() + ": " + playerInfo.getStack()));
        });
    }

    public void setToStage(Stage primaryStage) {
        verticalSeparator.setPrefHeight(720);
        try {
            bank.setText("Bank: " + poker.getMoneyInBank());
            nickName.setText(playersInfo.get(0).getId() + ": " + poker.getPlayerStack(playersInfo.get(0).getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        horizontalSeparator.setPrefWidth(1000);
        sb.getChildren().add(statusBar);
        sb.setAlignment(Pos.CENTER);
        activePlayersList.setMouseTransparent(true);

        root.add(new Label("Active players:"), 0, 0, 1, 1);
        root.add(activePlayersList, 0, 1, 1, 3);
        root.add(new Label("Fold players:"), 0, 4, 1, 1);
        root.add(foldPlayersList, 0, 5, 1, 3);
        root.add(verticalSeparator, 1, 0, 1, GridPane.REMAINING);
        root.add(sb, 2, 0, GridPane.REMAINING, 1);
        root.add(bank, 2, 1, GridPane.REMAINING, 1);
        root.add(horizontalSeparator, 2, 3, GridPane.REMAINING, 1);
        root.add(nickName, 2, 4, GridPane.REMAINING, 1);
        root.add(call, 5, 5, 1, 1);
        root.add(fold, 6, 5, 1, 1);
        root.add(raise, 7, 5, 1, 1);
        root.add(check, 8, 5, 1, 1);
        root.add(allIn, 9, 5, 1, 1);
        root.add(showDown, 10, 5, 1, 1);

        call.setOnAction(event -> {
            try {
                poker.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        check.setOnAction(event -> {
            try {
                poker.check();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        fold.setOnAction(event -> {
            try {
                poker.fold();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        primaryStage.setScene(new Scene(root, 1200, 720));
    }


    @Override
    public void stateChanged(String stateName) {
        this.stateName = stateName;
        System.out.println(stateName);
    }

    @Override
    public void gameIsOver(List<PlayerInfo> playersInfo) {
        this.playersInfo = playersInfo;
        poker = new Poker();
        poker.setUp(playersInfo, 1);
    }
}
