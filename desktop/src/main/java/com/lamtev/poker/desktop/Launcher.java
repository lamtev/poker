package com.lamtev.poker.desktop;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

@SuppressWarnings("WeakerAccess")
public class Launcher extends Application {

    //TODO хотелось бы видеть документацию к коду, чтобы была возможность сравнить что должен делать метод, и что он делает (если писать её во время создания новых методов, то это почти не занимает времени)
    //TODO добавь правила игры, потому что сейчас у тебя получается узкоспециализированное приложение (только для тех, кто знает правила игры в определённый тип покера), и тому кто будет делать ревью в будущем помогут лучше разобраться

    //TODO можно задать пустое имя и можно задать слишком длинное имя (если задать слишком длинное, то играть становиться критически неудобно), по-момему такого допускать нельзя
    //TODO задать минимальный размер окна, для которого все будет хорошо видно (потому что, когда я могу сжатьв всё так, чтобы стало невозможно играть - это ненормально)
    //TODO если дать ботам имена(человеческие), то это будет выглядеть приятнее
    //TODO сделать кнопку "Старт" в настройках больше и отодвинуть от остального (пользователь не должен долго думать куда ему нажать для начала игры, это должно сразу бросаться в глаза)

    private static final double SCALE_FACTOR = 0.8;
    private static final Rectangle2D screenRect = Screen.getPrimary().getVisualBounds();
    public static final double width = screenRect.getWidth() * SCALE_FACTOR;
    public static final double height = screenRect.getHeight() * SCALE_FACTOR;

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
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setResizable(false);
        stage.centerOnScreen();
    }

}
