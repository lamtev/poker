package com.lamtev.poker.desktop;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Launcher extends Application {

    private GridPane grid = new GridPane();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Texas Hold'em Poker");
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Button btn = new Button("Start game");
        btn.setOnAction(event -> {
            Settings settings = new Settings(primaryStage);
            grid = settings.getGridPane();
            Scene scene = new Scene(grid, 1200, 720);
            primaryStage.setScene(scene);
            primaryStage.show();
        });
        grid.add(btn, 0, 0);
        Scene scene = new Scene(grid, 1200, 720);
        primaryStage.setScene(scene);
        primaryStage.show();

    }



    public static void main(String[] args) {
        launch(args);
    }
}
