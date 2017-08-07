package com.lamtev.poker.desktop;

import com.lamtev.poker.core.ai.SimpleAI;
import com.lamtev.poker.core.api.*;
import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.states.exceptions.RoundOfPlayIsOverException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;
import java.util.stream.Collectors;

import static com.lamtev.poker.desktop.Launcher.HEIGHT;
import static com.lamtev.poker.desktop.Launcher.WIDTH;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toSet;
import static javafx.geometry.Pos.CENTER;

public class Game implements Play {

    private static final Image CARD_BACK_IMAGE = new Image("pics/back_side.png");
    private static final double COMMUNITY_CARD_HEIGHT = HEIGHT / 3;
    private static final double COMMUNITY_CARD_WIDTH = COMMUNITY_CARD_HEIGHT / 1.533;
    private static final double HOLE_CARD_HEIGHT = WIDTH / 15;
    private static final double HOLE_CARD_WIDTH = WIDTH / 22.8;
    private static final double DEALER_BUTTON_SIZE = HEIGHT / 12.875;
    private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 50;

    private Stage primaryStage;
    private RoundOfPlay poker;
    private int smallBlindSize;
    private String currentPlayerId;
    private final List<Player> players = new ArrayList<>();
    private final User user;
    private final List<AI> ais = new ArrayList<>();
    private final List<String> nicks;
    private final MoveAbility moveAbility = new MoveAbility();

    private HBox playersView = new HBox();
    private HBox communityCardsView = new HBox();
    private VBox root = new VBox();
    private VBox sb = new VBox();
    private Label statusBar = new Label();
    private Label moneyInBankLabel = new Label();
    private Button call = new Button("call");
    private Button fold = new Button("fold");
    private Button raise = new Button("raise");
    private Button check = new Button("check");
    private Button allIn = new Button("all in");
    private Button showDown = new Button("show down");
    private Separator horizontalSeparator = new Separator(Orientation.HORIZONTAL);
    private HBox buttons;

    Game(String userName, List<String> aiNames, int stackSize) {
        aiNames.forEach(it -> ais.add(new SimpleAI(it, stackSize)));
        user = new User(userName, stackSize);
        players.add(user);
        players.addAll(ais);
        nicks = players.stream().map(Player::id).collect(Collectors.toList());
        smallBlindSize = stackSize / BUTTON_WIDTH;
        communityCardsView.setPrefHeight(COMMUNITY_CARD_HEIGHT);
        communityCardsView.setPrefWidth(WIDTH);
    }

    void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        horizontalSeparator.setPrefWidth(WIDTH);
        sb.getChildren().add(statusBar);
        sb.setAlignment(CENTER);

        root.getChildren().add(sb);
        root.getChildren().add(horizontalSeparator);
        root.getChildren().add(communityCardsView);
        VBox moneyInBank = new VBox();
        moneyInBank.setAlignment(CENTER);
        moneyInBank.getChildren().add(moneyInBankLabel);
        root.getChildren().add(moneyInBank);
        root.getChildren().add(playersView);
        buttons = new HBox();
        buttons.setAlignment(CENTER);
        buttons.setSpacing(10);
        buttons.getChildren().addAll(call, check, raise, allIn, fold, showDown);
        root.getChildren().add(buttons);
        Label nickNameLabel = new Label();
        nickNameLabel.setText(players.get(0).id());
        String dealerId = nicks.get(0);
        setUpButtons();

        configurePlayersView();

        startNewGame(players, dealerId);

        primaryStage.setScene(new Scene(root, 1200, 720));
    }

    private void configurePlayersView() {
        playersView.getChildren().clear();
        playersView.setAlignment(CENTER);
        playersView.setSpacing(5);

        players.forEach(it -> {
            VBox playerView = new VBox(2);
            playerView.setId(it.id());
            playerView.setAlignment(CENTER);

            playerView.getChildren().addAll(
                    asList(
                            buttonsHBox(it.id()), wagerLabel(), cardsHBox(),
                            nickLabel(it), stackLabel(it), currentLabel()
                    )
            );

            playersView.getChildren().add(playerView);
        });
    }

    private Label currentLabel() {
        Label currentLabel = new Label();
        currentLabel.setId("current");
        return currentLabel;
    }

    private Label stackLabel(Player it) {
        Label stackLabel = new Label(Integer.toString(it.stack()));
        stackLabel.setId("stack");
        return stackLabel;
    }

    private Label nickLabel(Player it) {
        Label nickLabel = new Label(it.id());
        nickLabel.setId("nick");
        return nickLabel;
    }

    private HBox cardsHBox() {
        HBox cardsHBox = new HBox(1);
        cardsHBox.setAlignment(CENTER);
        cardsHBox.setId("cards");
        List<ImageView> cards = new ArrayList<>();
        for (int i = 0; i < 2; ++i) cards.add(new ImageView());
        cards.forEach(it -> {
            it.setFitHeight(HOLE_CARD_HEIGHT);
            it.setFitWidth(HOLE_CARD_WIDTH);
        });
        cardsHBox.getChildren().addAll(cards);
        return cardsHBox;
    }

    private Label wagerLabel() {
        Label wagerLabel = new Label();
        wagerLabel.setId("wager");
        return wagerLabel;
    }

    private HBox buttonsHBox(String id) {
        HBox buttonsHBox = new HBox(1);
        buttonsHBox.setAlignment(CENTER);
        buttonsHBox.setId("buttons");
        List<ImageView> buttons = new ArrayList<>();
        if (id.equals(nicks.get(0))) {
            buttons.add(new ImageView("pics/dealer.png"));
        }
        if (id.equals(nicks.get(1))) {
            buttons.add(new ImageView("pics/small-blind.png"));
        }
        if (nicks.size() > 2 && id.equals(nicks.get(2)) ||
                nicks.size() == 2 && id.equals(nicks.get(0))) {
            buttons.add(new ImageView("pics/big-blind.png"));
        }
        buttons.forEach(it -> {
            it.setFitWidth(DEALER_BUTTON_SIZE);
            it.setFitHeight(DEALER_BUTTON_SIZE);
        });
        buttonsHBox.getChildren().addAll(buttons);
        return buttonsHBox;
    }

    private void startNewGame(List<Player> players, String dealerId) {
        statusBar.setText("Welcome to Texas Hold'em Poker!!!");
        communityCardsView.getChildren().clear();

        poker = new PokerBuilder()
                .registerPlayers(players)
                .setDealerId(dealerId)
                .setSmallBlindWager(smallBlindSize)
                .registerPlay(this)
                .create();

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
        call.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
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
        allIn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
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
        fold.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
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
        check.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
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
        showDown.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
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
        raise.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        raise.setId("raise");
        raise.setOnAction(event -> {
            TextInputDialog dialogWindow = new TextInputDialog("" + BUTTON_HEIGHT);
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
                            sm.start(primaryStage);
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
                            sm.start(primaryStage);
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
        VBox playerView = (VBox) playersView.getChildren().stream()
                .filter(it -> it.getId().equals(playerId))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        playerView.getChildren().get(2).setVisible(false);
        Label currentLabel = (Label) playerView.getChildren().get(5);
        currentLabel.setText("fold");
    }

    @Override
    public void playerMoneyUpdated(String playerId, int playerStack, int playerWager) {
        if (user.id().equals(playerId)) {
            user.setStack(playerStack);
            user.setWager(playerWager);
        }
        VBox playerTableView = (VBox) playersView.getChildren().stream()
                .filter(it -> it.getId().equals(playerId))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        playerTableView.getChildren().set(1, new Label(Integer.toString(playerWager)));
        playerTableView.getChildren().set(4, new Label(Integer.toString(playerStack)));
        players.forEach(System.out::println);
    }

    @Override
    public void currentPlayerChanged(String currentPlayerId) {
        System.out.println(currentPlayerId);
        this.currentPlayerId = currentPlayerId;
        playersView.getChildren().forEach(it -> {
            Label currentLabel = (Label) ((VBox) it).getChildren().get(5);
            if (it.getId().equals(currentPlayerId)) {
                currentLabel.setText("current");
            } else {
                currentLabel.setText("fold".equals(currentLabel.getText()) ? "fold" : "");
            }
        });
        if (user.id().equals(currentPlayerId)) {
            buttons.getChildren().forEach(it -> {
                switch (it.getId()) {
                    case "allIn":
                        it.setDisable(!moveAbility.allInIsAble());
                        break;
                    case "call":
                        it.setDisable(!moveAbility.callIsAble());
                        break;
                    case "check":
                        it.setDisable(!moveAbility.checkIsAble());
                        break;
                    case "fold":
                        it.setDisable(!moveAbility.foldIsAble());
                        break;
                    case "raise":
                        it.setDisable(!moveAbility.raiseIsAble());
                        break;
                    case "showDown":
                        it.setDisable(!moveAbility.showdownIsAble());
                        break;
                    default:
                        throw new RuntimeException();
                }
            });
        } else {
            buttons.getChildren().forEach(it -> it.setDisable(true));
            makeAMoveOfAI();
        }
    }

    @Override
    public void communityCardsDealt(List<Card> addedCommunityCards) {
        communityCardsView.setAlignment(CENTER);
        communityCardsView.setSpacing(30);
        addedCommunityCards.forEach(card -> {
            ImageView cardView = new ImageView("pics/" + card.toString() + ".png");
            cardView.setFitHeight(COMMUNITY_CARD_HEIGHT);
            cardView.setFitWidth(COMMUNITY_CARD_WIDTH);
            communityCardsView.getChildren().add(cardView);
        });
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
        playersView.getChildren().forEach(it -> {
            VBox playerView = (VBox) it;
            HBox cards = (HBox) playerView.getChildren().get(2);
            if (it.getId().equals(user.id())) {
                int i = 0;
                for (Card card : user.cards()) {
                    ImageView imageView = (ImageView) cards.getChildren().get(i);
                    imageView.setImage(new Image("pics/" + card.toString() + ".png"));
                    ++i;
                }
            } else {
                for (int i = 0; i < 2; ++i) {
                    ImageView imageView = (ImageView) cards.getChildren().get(i);
                    imageView.setImage(CARD_BACK_IMAGE);
                }
            }
        });
    }

    @Override
    public void playerAllinned(String playerId) {
        statusBar.setText(playerId + " made all in");
    }

    @Override
    public void playerCalled(String playerId) {
        statusBar.setText(playerId + " called");
    }

    @Override
    public void playerChecked(String playerId) {
        statusBar.setText(playerId + " checked");
    }

    @Override
    public void playerRaised(String playerId) {
        statusBar.setText(playerId + " raised");
    }

    @Override
    public void playerShowedDown(String playerId, PokerHand hand) {
        statusBar.setText(playerId + " showed down: " + hand.getName());
        VBox playerView = (VBox) playersView.getChildren().stream()
                .filter(it -> it.getId().equals(playerId))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        HBox cardsHBox = (HBox) playerView.getChildren().get(2);
        List<Card> cards = players.stream()
                .filter(it -> it.id().equals(playerId))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .cards();
        int i = 0;
        for (Card card : cards) {
            ImageView imageView = (ImageView) cardsHBox.getChildren().get(i);
            imageView.setImage(new Image("pics/" + card.toString() + ".png"));
            ++i;
        }
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
