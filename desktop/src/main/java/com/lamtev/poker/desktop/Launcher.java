package com.lamtev.poker.desktop;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

@SuppressWarnings("WeakerAccess")
public class Launcher extends Application {

    //TODO хотелось бы видеть документацию к коду, чтобы была возможность сравнить что должен делать метод, и что он делает (если писать её во время создания новых методов, то это почти не занимает времени)
    //TODO добавь правила игры, потому что сейчас у тебя получается узкоспециализированное приложение (только для тех, кто знает правила игры в определённый тип покера), и тому кто будет делать ревью в будущем помогут лучше разобраться

    //TODO сделать кнопку "Старт" в настройках больше и отодвинуть от остального (пользователь не должен долго думать куда ему нажать для начала игры, это должно сразу бросаться в глаза)

    private static final double SCALE_FACTOR = 0.8;
    private static final Rectangle2D screenRect = Screen.getPrimary().getVisualBounds();
    public static final double WIDTH = screenRect.getWidth() * SCALE_FACTOR;
    public static final double HEIGHT = screenRect.getHeight() * SCALE_FACTOR;

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
        stage.setTitle("Poker");
        stage.getIcons().add(new Image("pics/icon-64.png"));
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.setResizable(false);
        stage.centerOnScreen();
    }

}
