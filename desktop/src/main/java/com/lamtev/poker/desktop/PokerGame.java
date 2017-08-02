package com.lamtev.poker.desktop;

import com.lamtev.poker.core.api.*;
import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.states.exceptions.RoundOfPlayIsOverException;
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
import java.util.stream.Collectors;

import static com.lamtev.poker.desktop.Launcher.height;
import static com.lamtev.poker.desktop.Launcher.width;
import static java.util.stream.Collectors.toSet;
import static javafx.scene.layout.GridPane.REMAINING;

public class PokerGame implements Play {

    private Stage primaryStage;
    private RoundOfPlay poker;
    private int smallBlindSize;
    private String currentPlayerId;
    private List<Card> communityCards = new ArrayList<>();
    private List<Player> players;
    private User user;
    private List<AI> ais;
    private List<String> nicks;
    private List<String> showedDown = new ArrayList<>();
    private final MoveAbility moveAbility = new MoveAbility();

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
    private HBox buttons;

    PokerGame(List<Player> players, User user, List<AI> ais) {
        this.players = players;
        this.user = user;
        this.ais = ais;
        nicks = players.stream().map(Player::id).collect(Collectors.toList());
        smallBlindSize = players.get(0).stack() / 100;
    }

    void setToStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        verticalSeparator.setPrefHeight(height);
        horizontalSeparator.setPrefWidth(width - activePlayersList.getWidth());
        sb.getChildren().add(statusBar);
        sb.setAlignment(Pos.CENTER);
        activePlayersList.setMouseTransparent(true);
        foldPlayersList.setMouseTransparent(true);

        activePlayersList.setPrefWidth(width / 8);
        foldPlayersList.setPrefWidth(width / 8);
        root.add(whoseTurn, 0, 0, 1, 1);
        root.add(new Label("Active players:"), 0, 1, 1, 1);
        root.add(activePlayersList, 0, 2, 1, 21);
        root.add(new Label("Fold players:"), 0, 23, 1, 1);
        root.add(foldPlayersList, 0, 24, 1, 21);
        root.add(verticalSeparator, 1, 0, 1, REMAINING);
        root.add(sb, 2, 0, REMAINING, 1);
        root.add(moneyInBankLabel, 2, 1, REMAINING, 1);
        root.add(horizontalSeparator, 2, 3, REMAINING, 1);

        root.add(playersCardsViewHBox, 2, 25, REMAINING, 6);
        buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);
        buttons.getChildren().addAll(call, fold, raise, check, allIn, showDown);
        root.add(buttons, 4, 44, REMAINING, 1);

        Label nickNameLabel = new Label();
        nickNameLabel.setText(players.get(0).id());
        String dealerId = nicks.get(0);
        setUpButtons();
        startNewGame(players, dealerId);

        primaryStage.setScene(new Scene(root, 1200, 720));
    }

    private void startNewGame(List<Player> players, String dealerId) {
        statusBar.setText("Welcome to Texas Hold'em Poker!!!");
        showedDown.clear();
        communityCards.clear();
        communityCardsView.getChildren().clear();

        poker = new PokerBuilder()
                .registerPlayers(players)
                .setDealerId(dealerId)
                .setSmallBlindWager(smallBlindSize)
                .registerPlay(this)
                .create();

        updateActiveAndFoldPlayersLists();
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
        call.setId("call");
        call.setOnAction(event -> {
            try {
                poker.call();
            } catch (RuntimeException e) {
                e.printStackTrace();
            } catch (Exception e) {
                statusBar.setText(e.getMessage());
            }
        });
    }

    private void setUpAllInButton() {
        allIn.setPrefSize(100, 50);
        allIn.setId("allIn");
        allIn.setOnAction(event -> {
            try {
                poker.allIn();
            } catch (RuntimeException e) {
                e.printStackTrace();
            } catch (Exception e) {
                statusBar.setText(e.getMessage());
            }
        });
    }

    private void setUpFoldButton() {
        fold.setPrefSize(100, 50);
        fold.setId("fold");
        fold.setOnAction(event -> {
            try {
                poker.fold();
            } catch (RuntimeException e) {
                e.printStackTrace();
            } catch (Exception e) {
                statusBar.setText(e.getMessage());
            }
        });
    }

    private void setUpCheckButton() {
        check.setPrefSize(100, 50);
        check.setId("check");
        check.setOnAction(event -> {
            try {
                poker.check();
            } catch (RuntimeException e) {
                e.printStackTrace();
            } catch (Exception e) {
                statusBar.setText(e.getMessage());
            }
        });
    }

    private void setUpShowDownButton() {
        showDown.setPrefSize(100, 50);
        showDown.setId("showDown");
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
        });
    }

    private void setUpRaiseButton() {
        raise.setPrefSize(100, 50);
        raise.setId("raise");
        raise.setOnAction(event -> {
            TextInputDialog dialogWindow = new TextInputDialog("" + 50);
            dialogWindow.setTitle("Raise");
            dialogWindow.setContentText("Input additional wager:");

            Optional<String> option = dialogWindow.showAndWait();
            option.ifPresent(o -> {
                try {
                    int additionalWager = Integer.parseInt(option.get());
                    poker.raise(additionalWager);
                } catch (NumberFormatException e) {
                    statusBar.setText("You input not a number");
                } catch (RuntimeException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    statusBar.setText(e.getMessage());
                }
            });
        });
    }

    private void makeAMoveOfAI() {
        AI ai = ais.stream()
                .filter(it -> it.id().equals(currentPlayerId))
                .findFirst()
                .orElse(null);
        if (ai != null) {
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(2500),
                    ae -> ai.makeAMove()
            ));
            timeline.play();
        }
    }

    @Override
    public void stateChanged(String stateName) {
        System.out.println(stateName);
        if (stateName.equals("RoundOfPlayIsOverState")) {
            user.setActive(true);
            user.setWager(0);
            user.cards().clear();

            statusBar.setText("Round of play is over!");

            int userStack = user.stack();

            if (userStack == 0) {
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
                gameIsOverWindow.show();
                return;
            }

            Set<String> losers = players.stream()
                    .filter(it -> it.stack() <= 0)
                    .map(Player::id)
                    .collect(toSet());

            players.removeIf(it -> losers.contains(it.id()));
            nicks.removeIf(losers::contains);

            if (players.size() == 1) {
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
                youWonWindow.show();
                return;
            }

            smallBlindSize += (smallBlindSize >> 1);
            Collections.rotate(nicks, -1);
            String dealerId = nicks.get(0);
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(2500),
                    ae -> startNewGame(players, dealerId)
            ));
            timeline.play();
        }
    }

    @Override
    public void playerFold(String playerId) {
        statusBar.setText(playerId + " fold");
        if (user.id().equals(playerId)) {
            user.setActive(false);
        }
        updateActiveAndFoldPlayersLists();
        updatePlayersCardsView();

    }

    private void updatePlayersCardsView() {
        playersCardsViewHBox.getChildren().clear();

        playersCardsViewHBox.setAlignment(Pos.CENTER);
        playersCardsViewHBox.setSpacing(5);

        players.stream()
                .filter(Player::isActive)
                .forEach(it -> {
                    VBox playerIdAndCardsView = new VBox();
                    playerIdAndCardsView.setAlignment(Pos.CENTER);
                    playerIdAndCardsView.setSpacing(2);
                    playerIdAndCardsView.getChildren().add(new Label(it.id()));

                    HBox playerCardsView = new HBox();
                    playerCardsView.setAlignment(Pos.CENTER);
                    playerCardsView.setSpacing(1);

                    if (it.id().equals(user.id()) || showedDown.contains(it.id())) {
                        it.cards().forEach(card -> {
                            String pathToPic = "pics/" + card.toString() + ".png";
                            ImageView cardView = new ImageView(pathToPic);
                            cardView.setFitHeight(width / 15.8);
                            cardView.setFitWidth(width / 24);
                            playerCardsView.getChildren().add(cardView);
                        });
                    } else {
                        for (int i = 0; i < 2; ++i) {
                            String pathToPic = "pics/back_side.png";
                            ImageView cardView = new ImageView(pathToPic);
                            cardView.setFitHeight(width / 15.8);
                            cardView.setFitWidth(width / 24);
                            playerCardsView.getChildren().add(cardView);
                        }
                    }

                    playerIdAndCardsView.getChildren().add(playerCardsView);
                    playersCardsViewHBox.getChildren().add(playerIdAndCardsView);
                    playersCardsViewHBox.setAlignment(Pos.CENTER);
                });
    }

    @Override
    public void playerMoneyUpdated(String playerId, int playerStack, int playerWager) {
        if (user.id().equals(playerId)) {
            user.setStack(playerStack);
            user.setWager(playerWager);
        }
        updateActiveAndFoldPlayersLists();
        players.forEach(System.out::println);
    }

    private void updateActiveAndFoldPlayersLists() {
        activePlayersList.getItems().clear();
        foldPlayersList.getItems().clear();
        players.forEach(it -> {
            String playerInfo = it.id() + ": " + it.stack() + " " + it.wager();
            if (it.id().equals(nicks.get(0))) {
                playerInfo += "  Dealer";
            } else if (it.id().equals(nicks.get(1))) {
                playerInfo += " Small Blind";
            } else if (it.id().equals(nicks.size() == 2 ? nicks.get(0) : nicks.get(2))) {
                playerInfo += " Big Blind";
            }
            Label label = new Label(playerInfo);
            if (it.isActive()) {
                activePlayersList.getItems().add(label);
            } else {
                foldPlayersList.getItems().add(label);
            }
        });
    }

    @Override
    public void currentPlayerChanged(String currentPlayerId) {
        System.out.println(currentPlayerId);
        this.currentPlayerId = currentPlayerId;
        whoseTurn.setText("Whose turn: " + currentPlayerId);
        if (user.id().equals(currentPlayerId)) {
            buttons.getChildren().forEach(it -> {
                switch (it.getId()) {
                    case "allIn":
                        it.setVisible(moveAbility.allInIsAble());
                        break;
                    case "call":
                        it.setVisible(moveAbility.callIsAble());
                        break;
                    case "check":
                        it.setVisible(moveAbility.checkIsAble());
                        break;
                    case "fold":
                        it.setVisible(moveAbility.foldIsAble());
                        break;
                    case "raise":
                        it.setVisible(moveAbility.raiseIsAble());
                        break;
                    case "showDown":
                        it.setVisible(moveAbility.showdownIsAble());
                        break;
                    default:
                        throw new RuntimeException();
                }
            });
        } else {
            buttons.getChildren().forEach(it -> it.setVisible(false));
            makeAMoveOfAI();
        }
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
        root.add(communityCardsView, 2, 4, REMAINING, 20);
    }

    @Override
    public void bankMoneyUpdated(int money, int wager) {
        moneyInBankLabel.setText("Bank: " + money);
    }

    @Override
    public void blindWagersPlaced() {

    }

    @Override
    public void holeCardsDealt(Map<String, List<Card>> playerIdToCards) {
        user.setCards(playerIdToCards.get(user.id()));
        updatePlayersCardsView();
    }

    @Override
    public void playerAllinned(String playerId) {
        statusBar.setText(playerId + " made all in");
        updateActiveAndFoldPlayersLists();
    }

    @Override
    public void playerCalled(String playerId) {
        statusBar.setText(playerId + " called");
        updateActiveAndFoldPlayersLists();
    }

    @Override
    public void playerChecked(String playerId) {
        statusBar.setText(playerId + " checked");
        updateActiveAndFoldPlayersLists();
    }

    @Override
    public void playerRaised(String playerId) {
        statusBar.setText(playerId + " raised");
        updateActiveAndFoldPlayersLists();
    }

    @Override
    public void playerShowedDown(String playerId, PokerHand hand) {
        showedDown.add(playerId);
        statusBar.setText(playerId + " showed down: " + hand.getName());
        updatePlayersCardsView();
        updateActiveAndFoldPlayersLists();
    }

    @Override
    public void callAbilityChanged(boolean flag) {
        moveAbility.setCallIsAble(flag);
    }

    @Override
    public void raiseAbilityChanged(boolean flag) {
        moveAbility.setRaiseIsAble(flag);
    }

    @Override
    public void allInAbilityChanged(boolean flag) {
        moveAbility.setAllInIsAble(flag);
    }

    @Override
    public void checkAbilityChanged(boolean flag) {
        moveAbility.setCheckIsAble(flag);
    }

    @Override
    public void foldAbilityChanged(boolean flag) {
        moveAbility.setFoldIsAble(flag);
    }

    @Override
    public void showDownAbilityChanged(boolean flag) {
        moveAbility.setShowdownIsAble(flag);
    }

}
