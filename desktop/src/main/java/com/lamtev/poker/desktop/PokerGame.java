package com.lamtev.poker.desktop;

import com.lamtev.poker.core.api.*;
import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Cards;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

    private HBox communityCardsView = new HBox();
    private List<VBox> playersCardsView = new ArrayList<>();
    private Label whoseTurn = new Label();
    private GridPane root = new GridPane();
    private VBox sb = new VBox();
    private Label statusBar = new Label("Welcome to Texas Hold'em Poker!!!");
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

    public PokerGame(List<PlayerInfo> playersInfo) {
        playerNick = playersInfo.get(0).getId();
        poker = new Poker();
        smallBlindSize = playersInfo.get(0).getStack() / 100;
        playersInfo.forEach(
                playerInfo -> this.playersInfo.put(
                        playerInfo.getId(),
                        new PlayerExpandedInfo(playerInfo.getStack(), 0, true)
                )
        );
        setUpGame(playersInfo);
        nickNameLabel.setText(playersInfo.get(0).getId());
        setUpButtons();
    }

    @Override
    public void gameIsOver(List<PlayerInfo> playersInfo) {

    }

    private void setUpGame(List<PlayerInfo> playersInfo) {
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
        });
    }

    private void setUpShowDownButton() {
        showDown.setPrefSize(100, 50);
        showDown.setOnAction(event -> {
            try {
                poker.showDown();
            } catch (RuntimeException e) {
                e.printStackTrace();
            } catch (Exception e) {
                statusBar.setText(e.getMessage());
            }
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
        });
    }

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
//        for (int i = 0; i < 10; ++i) {
//            root.add(new VBox(), 2+i, 31, GridPane.REMAINING, 10);
//        }
//        HBox buttons = new HBox();
//        buttons.setAlignment(Pos.CENTER);
//        buttons.setSpacing(50);
//        buttons.getChildren().addAll(call, fold, raise, check, allIn, showDown);
//        root.add(buttons, 2, 44, GridPane.REMAINING, 2);
        root.add(call, 4, 44, 1, 1);
        root.add(fold, 6, 44, 1, 1);
        root.add(raise, 8, 44, 1, 1);
        root.add(check, 10, 44, 1, 1);
        root.add(allIn, 12, 44, 1, 1);
        root.add(showDown, 14, 44, 1, 1);
//        ImageView iv = new ImageView("https://lh3.googleusercontent.com/DKoidc0T3T1KvYC2stChcX9zwmjKj1pgmg3hXzGBDQXM8RG_7JjgiuS0CLOh8DUa7as=w300");
//        iv.setFitHeight(50);
//        iv.setFitWidth(50);
//        root.add(iv, 11, 5, 1, 1);


        primaryStage.setScene(new Scene(root, 1200, 720));
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

    @Override
    public void currentPlayerChanged(String playerId) {
        currentPlayerId = playerId;
        whoseTurn.setText("Whose turn: " + playerId);
    }


    @Override
    public void playerShowedDown(String playerId, PokerHand hand) {
        hands.put(playerId, hand);

        statusBar.setText(playerId + " showed down: " + hand.getName());

        playersCardsView.clear();
        playersCards.forEach((id, cards) -> {

            VBox playerCardsView = new VBox();
            playerCardsView.setSpacing(10);
            playerCardsView.setAlignment(Pos.CENTER);
            playerCardsView.setPrefHeight(115);
            playerCardsView.getChildren().add(new Label(id));
            HBox cardsBox = new HBox();
            cardsBox.setAlignment(Pos.CENTER);
            cardsBox.setSpacing(5);
            cards.forEach(card -> {
                String path;
                if (id.equals(playerId)) {
                    path = "pics/" + card.toString() + ".png";
                } else {
                    path = "pics/back_side2.png";
                }
                ImageView cardView = new ImageView(path);
                cardView.setFitWidth(65);
                cardView.setFitHeight(97.5);
                cardsBox.getChildren().add(cardView);
            });
            playerCardsView.getChildren().add(cardsBox);
            playersCardsView.add(playerCardsView);
        });

        int i = 2;
        for (VBox vb : playersCardsView) {
            root.add(vb, i += 2, 25, 2, 6);
        }
    }

    @Override
    public void preflopMade(Map<String, Cards> playerIdToCards) {
        playersCards = playerIdToCards;
        //TODO paint players cards other side to up
        playersCards.forEach((id, cards) -> {
            VBox playerCardsView = new VBox();
            playerCardsView.setSpacing(10);
            playerCardsView.setAlignment(Pos.CENTER);
            playerCardsView.setPrefHeight(115);
            playerCardsView.getChildren().add(new Label(id));
            HBox cardsBox = new HBox();
            cardsBox.setAlignment(Pos.CENTER);
            cardsBox.setSpacing(5);
            cards.forEach(card -> {
                String path;
                if (id.equals(playerNick)) {
                    path = "pics/" + card.toString() + ".png";
                } else {
                    path = "pics/back_side2.png";
                }
                ImageView cardView = new ImageView(path);
                cardView.setFitWidth(65);
                cardView.setFitHeight(97.5);
                cardsBox.getChildren().add(cardView);
            });
            playerCardsView.getChildren().add(cardsBox);
            playersCardsView.add(playerCardsView);
        });
        int i = 2;
        for (VBox vb : playersCardsView) {
            root.add(vb, i += 2, 25, 2, 6);
        }

    }

    @Override
    public void communityCardsAdded(List<Card> addedCommunityCards) {
        communityCards.addAll(addedCommunityCards);
        //TODO paint community cards
        communityCardsView.getChildren().clear();
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
