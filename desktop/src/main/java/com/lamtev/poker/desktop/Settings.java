package com.lamtev.poker.desktop;

import com.lamtev.poker.core.ai.SimpleAI;
import com.lamtev.poker.core.api.AI;
import com.lamtev.poker.core.api.Player;
import com.lamtev.poker.core.api.User;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static com.lamtev.poker.desktop.Launcher.height;
import static com.lamtev.poker.desktop.Launcher.width;
import static java.util.Arrays.asList;
import static javafx.scene.layout.GridPane.REMAINING;

class Settings {

    private User user;
    private List<Player> players = new ArrayList<>();
    private List<AI> ais = new ArrayList<>();
    private PokerGame pokerGame;
    private int numberOfOpponents;
    private int playerStackSize;

    void setToStage(Stage primaryStage) {
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setPrefWidth(width);
        root.setPrefHeight(height);

        Label typeYourNick = new Label("Type your nickname:");
        Label chooseNumberOfOpponents = new Label("Choose number of opponents:");
        Label choosePlayerStack = new Label("Choose player stack size:");
        TextField playerNick = new TextField("User");
        ChoiceBox<Integer> numbersOfOpponents = new ChoiceBox<>();
        Slider slider = new Slider(1, 9, 2);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        slider.setSnapToTicks(true);
        ChoiceBox<Integer> playerStackSizes = new ChoiceBox<>();
        playerStackSizes.getItems().addAll(1000, 2000, 5000, 10000, 15000, 25000, 50000);
        playerStackSizes.setValue(1000);
        Button cancel = new Button("Cancel");
        Button start = new Button("Start");
        start.setOnAction(event -> {
            this.numberOfOpponents = (int) slider.getValue();
            this.playerStackSize = playerStackSizes.getValue();

            user = new User(playerNick.getText(), playerStackSize);

            for (int i = 0; i < numberOfOpponents; ++i) {
                ais.add(new SimpleAI("Bot " + (i + 1), playerStackSize));
            }
            players.add(user);
            players.addAll(ais);

            pokerGame = new PokerGame(players, user, ais);
            pokerGame.setToStage(primaryStage);
        });

        List<Control> controls = asList(
                typeYourNick, playerNick,
                chooseNumberOfOpponents, numbersOfOpponents,
                choosePlayerStack, playerStackSizes,
                start, cancel);
        controls.forEach(it -> {
            it.prefWidthProperty().bind(root.widthProperty().divide(7));
            it.prefHeightProperty().bind(root.heightProperty().divide(20));
        });

        root.add(typeYourNick, 0, 0, 1, 1);
        root.add(playerNick, 1, 0, 1, 1);
        root.add(chooseNumberOfOpponents, 0, 1, 1, 1);
        root.add(slider, 1, 1, 1, 1);
        root.add(choosePlayerStack, 0, 2, 1, 1);
        root.add(playerStackSizes, 1, 2, 1, 1);
        root.add(start, 0, 3, REMAINING, REMAINING);
        root.add(cancel, 1, 3, REMAINING, REMAINING);
        root.setHgap(width / 40);
        root.setVgap(height / 40);

        Scene scene = new Scene(root, 1200, 720);
        primaryStage.setScene(scene);
    }

}
