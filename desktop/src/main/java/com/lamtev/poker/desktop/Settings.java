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

public class Settings {

    Stage primaryStage;
    GridPane gp = new GridPane();

    public Settings(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public GridPane getGridPane() {

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
                int numberOfCompetitors = Integer.parseInt(rb.getText());
                TexasHoldemPoker thp = new TexasHoldemPoker(primaryStage);
                gp = thp.getGridPane(numberOfCompetitors);
                Scene scene = new Scene(gp, 1200, 720);
                primaryStage.setScene(scene);
                primaryStage.show();
            }
        }));
        gp.add(submit, 0, 6);
        return gp;
    }

}
