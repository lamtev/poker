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
import javafx.stage.Stage;

import java.util.*;

public class PokerGame implements CommunityCardsListener, CurrentPlayerListener, GameIsOverListener, MoveAbilityListener, PlayerFoldListener, PlayerShowedDownListener, PreflopMadeListener, StateChangedListener, WagerPlacedListener {

    private List<PlayerInfo> playersInfo;
    private PokerAPI poker = new Poker();
    private String stateName;
    private int smallBlindSize;
    private long bank;
    private List<String> foldPlayersIds = new ArrayList<>();
    private List<String> activePlayersIds = new ArrayList<>();
    private String currentPlayerId;
    private Map<String, PokerHand> hands = new HashMap<>();
    private Map<String, Cards> playersCards = new HashMap<>();
    private List<Card> communityCards = new ArrayList<>();
    private Map<String, PlayerMoney> playersMoney = new HashMap<>();

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
        this.playersInfo = playersInfo;
        smallBlindSize = playersInfo.get(0).getStack() / 100;
        setUpGame(playersInfo);
        nickNameLabel.setText(playersInfo.get(0).getId());
        setUpButtons();


        playersInfo.forEach(playerInfo -> activePlayersIds.add(playerInfo.getId()));
        playersInfo.subList(2, playersInfo.size()).forEach(
                playerInfo -> playersMoney.put(playerInfo.getId(), new PlayerMoney(playerInfo.getStack(), 0))
        );
        updateActivePlayersList();
        updateBank();
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

    private void updateActivePlayersList() {
        activePlayersList.getItems().clear();
        activePlayersIds.forEach(id -> activePlayersList.getItems().add(
                new Label(id + ": " +
                        playersMoney.get(id).getStack() + " " +
                        playersMoney.get(id).getWager())
                )
        );
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
        verticalSeparator.setPrefHeight(720);
        horizontalSeparator.setPrefWidth(1000);
        sb.getChildren().add(statusBar);
        sb.setAlignment(Pos.CENTER);
        activePlayersList.setMouseTransparent(true);
        foldPlayersList.setMouseTransparent(true);

        root.add(new Label("Active players:"), 0, 0, 1, 1);
        root.add(activePlayersList, 0, 1, 1, 3);
        root.add(new Label("Fold players:"), 0, 4, 1, 1);
        root.add(foldPlayersList, 0, 5, 1, 3);
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
        ImageView iv = new ImageView("https://lh3.googleusercontent.com/DKoidc0T3T1KvYC2stChcX9zwmjKj1pgmg3hXzGBDQXM8RG_7JjgiuS0CLOh8DUa7as=w300");
        iv.setFitHeight(50);
        iv.setFitWidth(50);
        root.add(iv, 11, 5, 1, 1);


        primaryStage.setScene(new Scene(root, 1200, 720));
    }


    @Override
    public void stateChanged(String stateName) {
        this.stateName = stateName;
        System.out.println(stateName);
    }

    @Override
    public void gameIsOver(List<PlayerInfo> playersInfo) {
        this.playersInfo = playersInfo;
        System.out.println(playersInfo.get(0));
        System.out.println(playersInfo.get(1));
        Collections.rotate(this.playersInfo, -1);
        activePlayersIds.removeIf(activePlayerId -> playersMoney.get(activePlayerId).getStack() <= 0);
        Collections.rotate(activePlayersIds, -1);
        System.out.println(playersInfo.get(0));
        System.out.println(playersInfo.get(1));
        smallBlindSize += (smallBlindSize >> 1);
        poker = new Poker();
        setUpGame(new ArrayList<PlayerInfo>() {{
            playersInfo.stream()
                    .filter(playerInfo -> playerInfo.getStack() > 0)
                    .forEach(this::add);
        }});
    }

    @Override
    public void playerFold(String foldPlayerId) {
        //TODO add checking for Human-player fold
        foldPlayersIds.add(foldPlayerId);
        foldPlayersList.getItems().add(new Label(foldPlayerId));
        activePlayersIds.remove(foldPlayersIds.get(foldPlayersIds.size() - 1));
        updateActivePlayersList();
    }

    @Override
    public void wagerPlaced(String playerId, PlayerMoney playerMoney, int bank) {
        this.bank = bank;
        playersMoney.put(playerId, playerMoney);
        updateActivePlayersList();
        updateBank();
    }

    private void updateBank() {
        moneyInBankLabel.setText("Bank: " + bank);
    }

    @Override
    public void currentPlayerChanged(String playerId) {
        currentPlayerId = playerId;
    }


    @Override
    public void playerShowedDown(String playerId, PokerHand hand) {
        hands.put(playerId, hand);
        statusBar.setText(playerId + " showed down: " + hand.getName());
    }


    @Override
    public void preflopMade(Map<String, Cards> playerIdToCards) {
        playersCards = playerIdToCards;
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
