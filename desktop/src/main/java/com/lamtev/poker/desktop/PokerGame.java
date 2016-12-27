package com.lamtev.poker.desktop;

import com.lamtev.poker.core.api.*;
import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.states.exceptions.GameIsOverException;
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

public class PokerGame implements CommunityCardsListener, CurrentPlayerListener, GameIsOverListener, MoveAbilityListener, PlayerFoldListener, PlayerShowedDownListener, PreflopMadeListener, StateChangedListener, WagerPlacedListener {

    private Stage primaryStage;

    private String playerNick;
    private PokerAPI poker;
    private String stateName;
    private int smallBlindSize;
    private long bank;
    private String currentPlayerId;
    private Map<String, PokerHand> hands = new HashMap<>();
    private List<Card> communityCards = new ArrayList<>();
    private Map<String, PlayerExpandedInfo> playersInfo = new LinkedHashMap<>();
    private Map<String, Cards> playersCards = new LinkedHashMap<>();
    private List<String> showedDown = new ArrayList<>();
    private List<AI> ais;

    HBox playersCardsViewHBox = new HBox();
    private HBox communityCardsView = new HBox();
    private Label whoseTurn = new Label();
    private GridPane root = new GridPane();
    private VBox sb = new VBox();
    private Label statusBar = new Label();
    private Label moneyInBankLabel = new Label();
    private Label nickNameLabel = new Label();
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

    public void setToStage(Stage primaryStage) {
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

    public PokerGame(List<PlayerInfo> playersInfo, List<AI> ais) {
        this.ais = ais;
        ais.forEach(ai -> ai.setPokerGame(this));
        playerNick = playersInfo.get(0).getId();
        nickNameLabel.setText(playersInfo.get(0).getId());
        smallBlindSize = playersInfo.get(0).getStack() / 100;

        startNewGame(playersInfo);
        setUpButtons();
    }

    public void doCall() {
        call.fire();
    }

    public void doRaise(int additionalWager) {
        //raise.fire();
    }

    public void doCheck() {
        check.fire();
    }

    public void doAllIn() {
        allIn.fire();
    }

    public void doFold() {
        System.out.println("fold will be fired");
        fold.fire();
    }

    public void doShowDown() {
        showDown.fire();
    }

    private void startNewGame(List<PlayerInfo> playersInfo) {
        statusBar.setText("Welcome to Texas Hold'em Poker!!!");
        this.playersInfo.clear();
        playersInfo.forEach(
                playerInfo -> this.playersInfo.put(
                        playerInfo.getId(),
                        new PlayerExpandedInfo(playerInfo.getStack(), 0, true)
                )
        );
        showedDown.clear();
        playersCards.clear();
        communityCards.clear();
        hands.clear();
        communityCardsView.getChildren().clear();
        poker = new Poker();
        setUpGame(playersInfo);
    }

    private void setUpGame(List<PlayerInfo> playersInfo) {
        ais.forEach(ai -> {
            poker.addCurrentPlayerIdListener(ai);
            poker.addStateChangedListener(ai);
        });

        poker.addStateChangedListener(this);
        poker.addGameIsOverListener(this);
        poker.addPlayerShowedDownListener(this);
        poker.addCommunityCardsListener(this);
        poker.addWagerPlacedListener(this);
        poker.addPreflopMadeListener(this);
        poker.addPlayerFoldListener(this);
        poker.addCurrentPlayerIdListener(this);
        poker.addMoveAbilityListener(this);
        poker.setUp(playersInfo, smallBlindSize);
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
            updateButtonsAbility();
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
            updateButtonsAbility();
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
            updateButtonsAbility();
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
            updateButtonsAbility();
        });
    }

    private void setUpShowDownButton() {
        showDown.setPrefSize(100, 50);
        showDown.setOnAction(event -> {
            try {
                poker.showDown();
            } catch (GameIsOverException e) {
                statusBar.setText(e.getMessage());
            } catch (RuntimeException e) {
                e.printStackTrace();
            } catch (Exception e) {
                statusBar.setText(e.getMessage());
            }
            System.out.println(stateName);
            updateButtonsAbility();
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
            updateButtonsAbility();
        });
    }

    @Override
    public void stateChanged(String stateName) {
        this.stateName = stateName;
        System.out.println(stateName);
    }

    @Override
    public void playerFold(String foldPlayerId) {
        //TODO add checking for Human-player fold
        playersInfo.get(foldPlayerId).setActive(false);
        updateActiveAndFoldPlayersLists();
        updatePlayersCardsView();
    }

    private void updatePlayersCardsView() {
        playersCardsViewHBox.getChildren().clear();

        playersCardsViewHBox.setAlignment(Pos.CENTER);
        playersCardsViewHBox.setSpacing(5);

        playersInfo.forEach((id, info) -> {
            if (info.isActive()) {
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
    public void wagerPlaced(String playerId, PlayerMoney playerMoney, int bank) {
        int stack = playerMoney.getStack();
        int wager = playerMoney.getWager();
        playersInfo.get(playerId).setStack(stack);
        playersInfo.get(playerId).setWager(wager);
        updateBank(bank);
        updateActiveAndFoldPlayersLists();
    }

    private void updateActiveAndFoldPlayersLists() {
        activePlayersList.getItems().clear();
        foldPlayersList.getItems().clear();
        playersInfo.forEach((id, playerInfo) -> {
            if (playerInfo.isActive()) {
                activePlayersList.getItems().add(
                        new Label(id + ": " + playerInfo.getStack() + " " + playerInfo.getWager())
                );
            } else {
                foldPlayersList.getItems().add(
                        new Label(id + ": " + playerInfo.getStack() + " " + playerInfo.getWager())
                );
            }
        });

    }

    private void updateBank(int bank) {
        moneyInBankLabel.setText("Bank: " + bank);
        this.bank = bank;
    }

    private void updateButtonsAbility() {
//        updateButtonAbility(call);
//        updateButtonAbility(fold);
//        updateButtonAbility(raise);
//        updateButtonAbility(allIn);
//        updateButtonAbility(check);
//        updateButtonAbility(showDown);

    }

    private void updateButtonAbility(Button button) {
        if (currentPlayerId.equals(playerNick)) {
            button.setVisible(true);
        } else {
            button.setVisible(false);
        }
    }

    @Override
    public void gameIsOver(List<PlayerInfo> playersInfo) {
        statusBar.setText("Game is over!");

        if (this.playersInfo.get(playerNick).getStack() == 0) {
            Alert gameIsOverWindow = new Alert(Alert.AlertType.INFORMATION);
            gameIsOverWindow.setTitle("Game is over!!!");
            gameIsOverWindow.setContentText("You lost all your money!!!");
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
        playersInfo.removeIf(playerInfo -> playerInfo.getStack() <= 0);
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
        Collections.rotate(playersInfo, -1);
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(2500),
                ae -> startNewGame(playersInfo)
        ));
        timeline.play();
    }

    @Override
    public void currentPlayerChanged(String playerId) {
        currentPlayerId = playerId;
        whoseTurn.setText("Whose turn: " + playerId);
    }


    //TODO duplicate
    @Override
    public void playerShowedDown(String playerId, PokerHand hand) {
        hands.put(playerId, hand);
        showedDown.add(playerId);

        statusBar.setText(playerId + " showed down: " + hand.getName());
        updatePlayersCardsView();
    }

    @Override
    public void preflopMade(Map<String, Cards> playerIdToCards) {
        playersCards = playerIdToCards;
        updatePlayersCardsView();
    }

    @Override
    public void communityCardsAdded(List<Card> addedCommunityCards) {
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
