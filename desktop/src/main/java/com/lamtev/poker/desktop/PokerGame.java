package com.lamtev.poker.desktop;

import com.lamtev.poker.core.api.*;
import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Cards;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.*;

public class PokerGame implements CommunityCardsListener, CurrentPlayerListener, GameIsOverListener, MoveAbilityListener, PlayerFoldListener, PlayerShowedDownListener, PreflopMadeListener, StateChangedListener, WagerPlacedListener {

    private Stage primaryStage;

    private PokerAPI poker;
    private String stateName;
    private int smallBlindSize;
    private long bank;
    private String currentPlayerId;
    private Map<String, PokerHand> hands = new HashMap<>();
    private List<Card> communityCards = new ArrayList<>();
    private Map<String, PlayerExpandedInfo> playersInfo = new LinkedHashMap<>();

    private Label whoseTurn = new Label();
    private GridPane root = new GridPane();
    private HBox sb = new HBox();
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
        root.add(activePlayersList, 0, 2, 1, 3);
        root.add(new Label("Fold players:"), 0, 5, 1, 1);
        root.add(foldPlayersList, 0, 6, 1, 3);
        root.add(verticalSeparator, 1, 0, 1, GridPane.REMAINING);
        root.add(sb, 2, 0, GridPane.REMAINING, 1);
        root.add(moneyInBankLabel, 2, 1, GridPane.REMAINING, 1);
        root.add(horizontalSeparator, 2, 3, GridPane.REMAINING, 1);
        root.add(nickNameLabel, 2, 4, GridPane.REMAINING, 1);
        root.add(call, 5, 5, 1, 1);
        root.add(fold, 6, 5, 1, 1);
        root.add(raise, 7, 5, 1, 1);
        root.add(check, 8, 5, 1, 1);
        root.add(allIn, 9, 5, 1, 1);
        root.add(showDown, 10, 5, 1, 1);
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
    }


    @Override
    public void preflopMade(Map<String, Cards> playerIdToCards) {
        //TODO
    }

    @Override
    public void communityCardsAdded(List<Card> addedCommunityCards) {
        communityCards.addAll(addedCommunityCards);
        //TODO paint community cards
    }

    @Override
    public void callAbilityChanged(boolean flag) {

    }

    @Override
    public void raiseAbilityChanged(boolean flag) {

    }

    @Override
    public void allInAbilityChanged(boolean flag) {

    }

    @Override
    public void checkAbilityChanged(boolean flag) {

    }

    @Override
    public void foldAbilityChanged(boolean flag) {

    }

    @Override
    public void showDownAbilityChanged(boolean flag) {

    }
}
