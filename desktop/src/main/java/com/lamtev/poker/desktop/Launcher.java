package com.lamtev.poker.desktop;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Launcher extends Application {

    private static final double SCALE_FACTOR = 0.8;
    private static final Rectangle2D screenRect = Screen.getPrimary().getVisualBounds();
    private static final Image LOGO = new Image("pics/icon.png");
    static final double WIDTH = screenRect.getWidth() * SCALE_FACTOR;
    static final double HEIGHT = screenRect.getHeight() * SCALE_FACTOR;

    @Override
    public void start(Stage primaryStage) throws Exception {
        configureStage(primaryStage);
        StartMenu startMenu = new StartMenu();
        startMenu.start(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void configureStage(Stage stage) {
        stage.setTitle("Poker Texas Hold'em");
        stage.getIcons().add(LOGO);
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.setResizable(false);
        stage.centerOnScreen();
    }

}
