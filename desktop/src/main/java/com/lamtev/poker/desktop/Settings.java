package com.lamtev.poker.desktop;

import com.lamtev.poker.core.api.PlayerInfo;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Settings {

    private PokerGame pokerGame;
    private String playerNick;
    private int numberOfOpponents;
    private int playerStackSize;

    public void setToStage(Stage primaryStage) {
        FlowPane rootNode = new FlowPane(10, 10);
        rootNode.setAlignment(Pos.CENTER);
        rootNode.setOrientation(Orientation.VERTICAL);
        Label typeYourNick = new Label("Type your nickname");
        Label chooseNumberOfOpponents = new Label("Choose number of opponents");
        Label choosePlayerStack = new Label("Choose player stack size");
        TextField playerNick = new TextField("Player 1");
        ChoiceBox<Integer> numbersOfOpponents = new ChoiceBox<>();
        numbersOfOpponents.getItems().addAll(2, 3, 4, 5, 6);
        numbersOfOpponents.setValue(2);
        ChoiceBox<Integer> playerStackSizes = new ChoiceBox<>();
        playerStackSizes.getItems().addAll(1000, 2000, 5000, 10000, 15000, 25000, 50000);
        playerStackSizes.setValue(1000);
        Button start = new Button("Start");
        start.setOnAction(event -> {
            System.out.println("Nickname: " + playerNick.getText());
            System.out.println("Number of opponents: " + numbersOfOpponents.getValue());
            System.out.println("Stack size: " + playerStackSizes.getValue());
            this.playerNick = playerNick.getText();
            this.numberOfOpponents = numbersOfOpponents.getValue();
            this.playerStackSize = playerStackSizes.getValue();
            pokerGame = new PokerGame(createPlayersInfo());
            pokerGame.setToStage(primaryStage);
        });
        rootNode.getChildren().addAll(
                typeYourNick, playerNick,
                chooseNumberOfOpponents, numbersOfOpponents,
                choosePlayerStack, playerStackSizes,
                start
        );
        primaryStage.setScene(new Scene(rootNode, 1200, 720));
    }

    public List<PlayerInfo> createPlayersInfo() {
        List<PlayerInfo> playersInfo = new ArrayList<>();
        playersInfo.add(new PlayerInfo(playerNick, playerStackSize));
        for (int i = 0; i < numberOfOpponents; ++i) {
            playersInfo.add(new PlayerInfo("Bot " + i, playerStackSize));
        }
        return playersInfo;
    }

}
