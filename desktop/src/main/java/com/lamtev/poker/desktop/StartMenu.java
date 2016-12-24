package com.lamtev.poker.desktop;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartMenu {

    public void setToStage(Stage primaryStage) {
        FlowPane rootNode = new FlowPane(10, 10);
        rootNode.setOrientation(Orientation.VERTICAL);
        rootNode.setAlignment(Pos.CENTER);
        Button startGame = new Button("Start game");
        startGame.setPrefSize(150, 30);
        startGame.setOnAction(e -> {
            Settings settings = new Settings();
            settings.setToStage(primaryStage);
        });
        Button about = new Button("About");
        about.setPrefSize(150, 30);
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(startGame, about);
        rootNode.getChildren().add(vBox);
        primaryStage.setScene(new Scene(rootNode, 1200, 720));
    }

}
