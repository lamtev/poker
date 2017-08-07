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

    private static final int MAX_NICK_LENGTH = 15;
    private static final int MIN_NICK_LENGTH = 3;

    private int numberOfOpponents;
    private int stackSize;

    private final GridPane root = new GridPane();
    private final Label typeYourNick = new Label("Type your nickname:");
    private final Label chooseNumberOfOpponents = new Label("Choose number of opponents:");
    private final Label choosePlayerStack = new Label("Choose player stack size:");
    private final TextField playerNameField = new TextField("User");
    private final Slider numberOfOpponentsSlider = new Slider(1, 9, 2);
    private final ChoiceBox<Integer> stackSizeChoiceBox = new ChoiceBox<>();
    private final Button startButton = new Button("Start");
    private final Button cancelButton = new Button("Cancel");

    void start(Stage primaryStage) {
        configureLayout();
        configurePlayerNameField();
        configureNumberOfOpponentsSlider();
        configureStackSizeChoiceBox();
        configureStartButton(primaryStage);
        configureCancelButton(primaryStage);
        configureControlsSizes();
        primaryStage.setScene(new Scene(root, width, height));
    }

    private void configureLayout() {
        root.setAlignment(CENTER);
        root.setPrefSize(width, height);
        root.add(typeYourNick, 0, 0, 1, 1);
        root.add(playerNameField, 1, 0, 1, 1);
        root.add(chooseNumberOfOpponents, 0, 1, 1, 1);
        root.add(numberOfOpponentsSlider, 1, 1, 1, 1);
        root.add(choosePlayerStack, 0, 2, 1, 1);
        root.add(stackSizeChoiceBox, 1, 2, 1, 1);
        root.add(startButton, 0, 3, REMAINING, REMAINING);
        root.add(cancelButton, 1, 3, REMAINING, REMAINING);
        root.setHgap(width / 40);
        root.setVgap(height / 40);
    }

    private void configurePlayerNameField() {
        playerNameField.textProperty().addListener(observable -> {
            int length = playerNameField.getText().length();
            if (length > MAX_NICK_LENGTH) {
                String s = playerNameField.getText().substring(0, MAX_NICK_LENGTH);
                playerNameField.setText(s);
            } else if (length < MIN_NICK_LENGTH) {
                startButton.setDisable(true);
            } else {
                startButton.setDisable(false);
            }
        });
    }

    private void configureNumberOfOpponentsSlider() {
        numberOfOpponentsSlider.setShowTickLabels(true);
        numberOfOpponentsSlider.setShowTickMarks(true);
        numberOfOpponentsSlider.setMajorTickUnit(1);
        numberOfOpponentsSlider.setMinorTickCount(0);
        numberOfOpponentsSlider.setSnapToTicks(true);
    }

    private void configureStackSizeChoiceBox() {
        stackSizeChoiceBox.getItems().addAll(1000, 2000, 5000, 10_000, 15_000, 25_000, 50_000, 100_000);
        stackSizeChoiceBox.setValue(5000);
    }

    private void configureStartButton(Stage primaryStage) {
        startButton.setOnAction(event -> {
            numberOfOpponents = (int) numberOfOpponentsSlider.getValue();
            stackSize = stackSizeChoiceBox.getValue();
            String playerName = playerNameField.getText();
            List<String> aiNames = nNamesExceptThis(numberOfOpponents, playerName);
            new Game(playerName, aiNames, stackSize)
                    .start(primaryStage);
        });
    }

    private void configureCancelButton(Stage primaryStage) {
        cancelButton.setOnAction(event -> new StartMenu().start(primaryStage));
    }


    private void configureControlsSizes() {
        asList(typeYourNick, playerNameField,
                chooseNumberOfOpponents, numberOfOpponentsSlider,
                choosePlayerStack, stackSizeChoiceBox,
                startButton, cancelButton
        ).forEach(it -> {
            it.prefWidthProperty().bind(root.widthProperty().divide(7));
            it.prefHeightProperty().bind(root.heightProperty().divide(20));
        });
    }

}
