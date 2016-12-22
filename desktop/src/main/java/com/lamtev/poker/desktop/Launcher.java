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
            GridPane gp = new GridPane();
            gp.setAlignment(Pos.CENTER);
            gp.setHgap(10);
            gp.setVgap(10);
            gp.setPadding(new Insets(25, 25, 25, 25));
            gp.add(new Label("Type number of competitors"), 0, 0);
            ToggleGroup tg = new ToggleGroup();
            List<RadioButton> radioButtons = new ArrayList<>();
            for (int i = 3; i < 8; ++i) {
                RadioButton rb = new RadioButton(((Integer) i).toString());
                rb.setToggleGroup(tg);
                radioButtons.add(rb);
                gp.add(rb, 0, i-2);
            }
            Button submit = new Button("Submit");
            submit.setOnAction(e -> radioButtons.forEach(rb -> {
                if (rb.isSelected()) {
                    int numberOfCompetitor = Integer.parseInt(rb.getText());
                    //gp = game
                    //Scene scene = new Scene(grid, 1200, 720);
                    //primaryStage.setScene(scene);
                    //primaryStage.show();
                    System.out.println(numberOfCompetitor);
                }
            }));
            gp.add(submit, 0, 6);
            grid = gp;
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
