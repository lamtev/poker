package com.lamtev.poker.desktop;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

import static com.lamtev.poker.desktop.Launcher.height;
import static com.lamtev.poker.desktop.Launcher.width;
import static java.util.Arrays.asList;

class StartMenu {

    void start(Stage primaryStage) {
        VBox root = new VBox(height / 40);
        root.setAlignment(Pos.CENTER);
        root.setPrefWidth(width);
        root.setPrefHeight(height);

        Button startGame = new Button("Start game");
        startGame.setOnAction(e -> new Settings().start(primaryStage));

        Button about = new Button("About");

        List<Button> buttons = asList(startGame, about);
        buttons.forEach(it -> {
            it.prefWidthProperty().bind(root.widthProperty().divide(5));
            it.prefHeightProperty().bind(root.heightProperty().divide(15));
        });

        root.getChildren().addAll(buttons);

        Scene scene = new Scene(root, width, height);
        primaryStage.setScene(scene);
    }

}
