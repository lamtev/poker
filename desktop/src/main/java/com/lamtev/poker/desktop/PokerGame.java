package com.lamtev.poker.desktop;

import com.lamtev.poker.core.api.*;
import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.states.exceptions.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

public class PokerGame implements PokerPlay {

    private Stage primaryStage;

    private String playerNick;
    private RoundOfPlay poker;
    private String stateName;
    private int smallBlindSize;
    private String currentPlayerId;
    private List<Card> communityCards = new ArrayList<>();
    private Map<String, Map.Entry<Integer, Integer>> playersInfo = new LinkedHashMap<>();
    private Set<String> foldPlayers = new HashSet<>();
    private Map<String, List<Card>> playersCards = new LinkedHashMap<>();
    private List<String> showedDown = new ArrayList<>();

    private HBox playersCardsViewHBox = new HBox();
    private HBox communityCardsView = new HBox();
    private Label whoseTurn = new Label();
    private GridPane root = new GridPane();
    private VBox sb = new VBox();
    private Label statusBar = new Label();
    private Label moneyInBankLabel = new Label();
    private ListView<Label> activePlayersList = new ListView<>();
    private ListView<Label> foldPlayersList = new ListView<>();
    private Button call = new Button("call");
    private Button fold = new Button("fold");
    private Button raise = new Button("raise");
    private Button check = new Button("check");
    private Button allIn = new Button("all in");
    private Button showDown = new Button("show down");
    private Separator verticalSeparator = new Separator(Orientation.VERTICAL);
    private Separator horizontalSeparator = new Separator(Orientation.HORIZONTAL);

    PokerGame(List<PlayerIdStack> playersInfo) {
        playerNick = playersInfo.get(0).id();
        Label nickNameLabel = new Label();
        nickNameLabel.setText(playersInfo.get(0).id());
        smallBlindSize = playersInfo.get(0).stack() / 100;
        String dealerId = playersInfo.get(0).id();
        startNewGame(playersInfo, dealerId);
        setUpButtons();
    }

    void setToStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        verticalSeparator.setPrefHeight(720);
        horizontalSeparator.setPrefWidth(1000);
        sb.getChildren().add(statusBar);
        sb.setAlignment(Pos.CENTER);
        activePlayersList.setMouseTransparent(true);
        foldPlayersList.setMouseTransparent(true);

        root.add(whoseTurn, 0, 0, 1, 1);
        root.add(new Label("Active players:"), 0, 1, 1, 1);
        root.add(activePlayersList, 0, 2, 1, 21);
        root.add(new Label("Fold players:"), 0, 23, 1, 1);
        root.add(foldPlayersList, 0, 24, 1, 21);
        root.add(verticalSeparator, 1, 0, 1, GridPane.REMAINING);
        root.add(sb, 2, 0, GridPane.REMAINING, 1);
        root.add(moneyInBankLabel, 2, 1, GridPane.REMAINING, 1);
        root.add(horizontalSeparator, 2, 3, GridPane.REMAINING, 1);

        root.add(playersCardsViewHBox, 2, 25, GridPane.REMAINING, 6);

        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);
        buttons.getChildren().addAll(call, fold, raise, check, allIn, showDown);
        root.add(buttons, 4, 44, GridPane.REMAINING, 1);

        primaryStage.setScene(new Scene(root, 1200, 720));
    }

    private void startNewGame(List<PlayerIdStack> playersInfo, String dealerId) {
        statusBar.setText("Welcome to Texas Hold'em Poker!!!");
        this.playersInfo.clear();
        playersInfo.forEach(
                playerInfo -> this.playersInfo.put(
                        playerInfo.id(),
                        new AbstractMap.SimpleEntry<>(playerInfo.stack(), 0)
                )
        );
        foldPlayers.clear();
        showedDown.clear();
        playersCards.clear();
        communityCards.clear();
        communityCardsView.getChildren().clear();

        poker = new PokerBuilder()
                .registerPlayers(playersInfo)
                .setDealerId(dealerId)
                .setSmallBlindWager(smallBlindSize)
                .create();
        poker.subscribe(this);
        poker.start();
    }

    private void setUpButtons() {
        setUpCallButton();
        setUpCheckButton();
        setUpFoldButton();
        setUpShowDownButton();
        setUpRaiseButton();
        setUpAllInButton();
    }

    private void setUpCallButton() {
        call.setPrefSize(100, 50);
        call.setOnAction(event -> {
            try {
                String id = currentPlayerId;
                poker.call();
                statusBar.setText(id + " called");
            } catch (RuntimeException e) {
                e.printStackTrace();
            } catch (Exception e) {
                statusBar.setText(e.getMessage());
            }
            System.out.println(stateName);
        });
    }

    private void setUpAllInButton() {
        allIn.setPrefSize(100, 50);
        allIn.setOnAction(event -> {
            try {
                String id = currentPlayerId;
                poker.allIn();
                statusBar.setText(id + " all in");
            } catch (RuntimeException e) {
                e.printStackTrace();
            } catch (Exception e) {
                statusBar.setText(e.getMessage());
            }
            System.out.println(stateName);
        });
    }

    private void setUpFoldButton() {
        fold.setPrefSize(100, 50);
        fold.setOnAction(event -> {
            try {
                String id = currentPlayerId;
                poker.fold();
                statusBar.setText(id + " fold");
            } catch (RuntimeException e) {
                e.printStackTrace();
            } catch (Exception e) {
                statusBar.setText(e.getMessage());
            }
            System.out.println(stateName);
        });
    }

    private void setUpCheckButton() {
        check.setPrefSize(100, 50);
        check.setOnAction(event -> {
            try {
                String id = currentPlayerId;
                poker.check();
                statusBar.setText(id + " checked");
            } catch (RuntimeException e) {
                e.printStackTrace();
            } catch (Exception e) {
                statusBar.setText(e.getMessage());
            }
            System.out.println(stateName);
        });
    }

    private void setUpShowDownButton() {
        showDown.setPrefSize(100, 50);
        showDown.setOnAction(event -> {
            try {
                poker.showDown();
            } catch (RoundOfPlayIsOverException e) {
                statusBar.setText(e.getMessage());
            } catch (RuntimeException e) {
                e.printStackTrace();
            } catch (Exception e) {
                statusBar.setText(e.getMessage());
            }
            System.out.println(stateName);
        });
    }

    private void setUpRaiseButton() {
        raise.setPrefSize(100, 50);
        raise.setOnAction(event -> {

            TextInputDialog dialogWindow = new TextInputDialog("" + 50);
            dialogWindow.setTitle("Raise");
            dialogWindow.setContentText("Input additional wager:");

            Optional<String> option = dialogWindow.showAndWait();
            option.ifPresent(o -> {
                try {
                    String id = currentPlayerId;
                    int additionalWager = Integer.parseInt(option.get());
                    poker.raise(additionalWager);
                    statusBar.setText(id + " raised by " + additionalWager);
                } catch (NumberFormatException e) {
                    statusBar.setText("You input not a number");
                } catch (RuntimeException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    statusBar.setText(e.getMessage());
                }
            });
            System.out.println(stateName);

        });
    }

    @Override
    public void stateChanged(String stateName) {
        this.stateName = stateName;
        System.out.println(stateName);
    }

    @Override
    public void playerFold(String playerId) {
        //TODO add checking for Human-player fold
        foldPlayers.add(playerId);
        updateActiveAndFoldPlayersLists();
        updatePlayersCardsView();
    }

    private void updatePlayersCardsView() {
        playersCardsViewHBox.getChildren().clear();

        playersCardsViewHBox.setAlignment(Pos.CENTER);
        playersCardsViewHBox.setSpacing(5);

        playersInfo.forEach((id, info) -> {
            if (!foldPlayers.contains(id)) {
                VBox playerIdAndCardsView = new VBox();
                playerIdAndCardsView.setAlignment(Pos.CENTER);
                playerIdAndCardsView.setSpacing(2);
                playerIdAndCardsView.getChildren().add(new Label(id));

                HBox playerCardsView = new HBox();
                playerCardsView.setAlignment(Pos.CENTER);
                playerCardsView.setSpacing(1);

                playersCards.get(id).forEach(card -> {
                    String pathToPic;
                    if (id.equals(playerNick) || showedDown.contains(id)) {
                        pathToPic = "pics/" + card.toString() + ".png";
                    } else {
                        pathToPic = "pics/back_side2.png";
                    }
                    ImageView cardView = new ImageView(pathToPic);
                    cardView.setFitHeight(97.5);
                    cardView.setFitWidth(65);
                    playerCardsView.getChildren().add(cardView);
                });

                playerIdAndCardsView.getChildren().add(playerCardsView);

                playersCardsViewHBox.getChildren().add(playerIdAndCardsView);


            }
        });
    }

    @Override
    public void playerMoneyUpdated(String playerId, int playerStack, int playerWager) {
        playersInfo.remove(playerId);
        playersInfo.put(playerId, new AbstractMap.SimpleEntry<>(playerStack, playerWager));
        updateActiveAndFoldPlayersLists();
    }

    private void updateActiveAndFoldPlayersLists() {
        activePlayersList.getItems().clear();
        foldPlayersList.getItems().clear();
        playersInfo.forEach((id, playerInfo) -> {
            if (!foldPlayers.contains(id)) {
                activePlayersList.getItems().add(
                        new Label(id + ": " + playerInfo.getKey() + " " + playerInfo.getValue())
                );
            } else {
                foldPlayersList.getItems().add(
                        new Label(id + ": " + playerInfo.getKey() + " " + playerInfo.getValue())
                );
            }
        });

    }

    private void updateBank(int bank) {
        moneyInBankLabel.setText("Bank: " + bank);
    }

    @Override
    public void roundOfPlayIsOver(List<PlayerIdStack> playersInfo) {
        statusBar.setText("Game is over!");

        if (this.playersInfo.get(playerNick).getKey() == 0) {
            Alert gameIsOverWindow = new Alert(Alert.AlertType.INFORMATION);
            gameIsOverWindow.setTitle("Game is over!!!");
            gameIsOverWindow.setContentText("You have lost all your money!!!");
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(2500),
                    ae -> gameIsOverWindow.setOnCloseRequest(event -> {
                        StartMenu sm = new StartMenu();
                        sm.setToStage(primaryStage);
                    })
            ));
            timeline.play();
            gameIsOverWindow.showAndWait();
            return;
        }
        playersInfo.removeIf(playerInfo -> playerInfo.stack() <= 0);
        if (playersInfo.size() == 1) {
            Alert youWonWindow = new Alert(Alert.AlertType.INFORMATION);
            youWonWindow.setTitle("You won!!!");
            youWonWindow.setContentText("Congratulations!!!");
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(2500),
                    ae -> youWonWindow.setOnCloseRequest(event -> {
                        StartMenu sm = new StartMenu();
                        sm.setToStage(primaryStage);
                    })
            ));
            timeline.play();
            youWonWindow.showAndWait();
            return;
        }

        smallBlindSize += (smallBlindSize >> 1);
        List<PlayerIdStack> list = new ArrayList<>(playersInfo);
        Collections.copy(list, playersInfo);
        Collections.rotate(list, -1);
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(2500),
                ae -> startNewGame(playersInfo, list.get(0).id())
        ));
        timeline.play();
    }

    @Override
    public void currentPlayerChanged(String currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
        whoseTurn.setText("Whose turn: " + currentPlayerId);
    }


    @Override
    public void communityCardsDealt(List<Card> addedCommunityCards) {
        communityCards.addAll(addedCommunityCards);
        communityCardsView.getChildren().clear();
        communityCardsView.setAlignment(Pos.CENTER);
        communityCardsView.setSpacing(30);
        communityCards.forEach(card -> {
            ImageView cardView = new ImageView("pics/" + card.toString() + ".png");
            cardView.setFitHeight(230);
            cardView.setFitWidth(150);
            communityCardsView.getChildren().add(cardView);
        });
        root.getChildren().remove(communityCardsView);
        root.add(communityCardsView, 2, 4, GridPane.REMAINING, 20);
    }

    @Override
    public void bankMoneyUpdated(int money, int wager) {
        updateBank(money);
    }

    @Override
    public void blindWagersPlaced() {

    }

    @Override
    public void holeCardsDealt(Map<String, List<Card>> playerIdToCards) {
        playersCards = playerIdToCards;
        updatePlayersCardsView();
    }

    @Override
    public void playerAllinned(String playerId) {

    }

    @Override
    public void playerCalled(String playerId) {

    }

    @Override
    public void playerChecked(String playerId) {

    }

    @Override
    public void playerRaised(String playerId) {

    }

    @Override
    public void playerShowedDown(String playerId, PokerHand hand) {
        showedDown.add(playerId);

        statusBar.setText(playerId + " showed down: " + hand.getName());
        updatePlayersCardsView();
    }

    @Override
    public void callAbilityChanged(boolean flag) {
        //TODO implement
    }

    @Override
    public void raiseAbilityChanged(boolean flag) {
        //TODO implement
    }

    @Override
    public void allInAbilityChanged(boolean flag) {
        //TODO implement
    }

    @Override
    public void checkAbilityChanged(boolean flag) {
        //TODO implement
    }

    @Override
    public void foldAbilityChanged(boolean flag) {
        //TODO implement
    }

    @Override
    public void showDownAbilityChanged(boolean flag) {
        //TODO implement
    }
}
