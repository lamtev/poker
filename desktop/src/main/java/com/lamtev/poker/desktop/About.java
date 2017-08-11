package com.lamtev.poker.desktop;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

import static com.lamtev.poker.desktop.Launcher.HEIGHT;
import static com.lamtev.poker.desktop.Launcher.WIDTH;
import static java.util.Arrays.asList;
import static javafx.geometry.Pos.CENTER;

class About {

    void start(Stage primaryStage) {
        final VBox root = new VBox(25);
        final Button back = new Button("back");

        final List<Node> NODES = asList(
                new Label("Poker Texas Hold'em"),
                new Label("Project: https://github.com/lamtev/poker"),
                new Label("Developer: Anton Lamtev"),
                new Label("Mail to: antonlamtev@gmail.com"),
                new Label("Github: @lamtev"),
                back
        );

        back.prefWidthProperty().bind(root.widthProperty().divide(5));
        back.prefHeightProperty().bind(root.heightProperty().divide(15));

        root.setAlignment(CENTER);
        root.getChildren().addAll(NODES);

        back.setOnAction(e -> new StartMenu().start(primaryStage));
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
    }

}
