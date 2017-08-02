package com.lamtev.poker.desktop;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

import static com.lamtev.poker.desktop.Launcher.height;
import static com.lamtev.poker.desktop.Launcher.width;
import static com.lamtev.poker.desktop.Util.nNamesExceptThis;
import static java.util.Arrays.asList;
import static javafx.geometry.Pos.CENTER;
import static javafx.scene.layout.GridPane.REMAINING;

class Settings {

    private static final int MAX_NICK_LENGTH = 10;
    private static final int MIN_NICK_LENGTH = 3;
    private int numberOfOpponents;
    private int stackSize;

    void setToStage(Stage primaryStage) {
        GridPane root = new GridPane();
        root.setAlignment(CENTER);
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
        ChoiceBox<Integer> stackSizes = new ChoiceBox<>();
        stackSizes.getItems().addAll(1000, 2000, 5000, 10_000, 15_000, 25_000, 50_000, 100_000);
        stackSizes.setValue(5000);
        Button cancel = new Button("Cancel");
        Button start = new Button("Start");
        start.setOnAction(event -> {
            numberOfOpponents = (int) slider.getValue();
            stackSize = stackSizes.getValue();
            List<String> aiNames = nNamesExceptThis(numberOfOpponents, playerNick.getText());

            PokerGame pokerGame = new PokerGame(playerNick.getText(), aiNames, stackSize);
            pokerGame.setToStage(primaryStage);
        });

        playerNick.textProperty().addListener(observable -> {
            int length = playerNick.getText().length();
            if (length > MAX_NICK_LENGTH) {
                String s = playerNick.getText().substring(0, MAX_NICK_LENGTH);
                playerNick.setText(s);
            } else if (length < MIN_NICK_LENGTH) {
                start.setDisable(true);
            } else {
                start.setDisable(false);
            }
        });

        List<Control> controls = asList(
                typeYourNick, playerNick,
                chooseNumberOfOpponents, numbersOfOpponents,
                choosePlayerStack, stackSizes,
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
        root.add(stackSizes, 1, 2, 1, 1);
        root.add(start, 0, 3, REMAINING, REMAINING);
        root.add(cancel, 1, 3, REMAINING, REMAINING);
        root.setHgap(width / 40);
        root.setVgap(height / 40);

        Scene scene = new Scene(root, width, height);
        primaryStage.setScene(scene);
    }

}
