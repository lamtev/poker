package com.lamtev.poker.desktop;

import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {

    private StartMenu startMenu;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Poker");
        startMenu = new StartMenu();
        setScene(primaryStage);
        primaryStage.show();
    }

    private void setScene(Stage primaryStage) {
        startMenu.setToStage(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
