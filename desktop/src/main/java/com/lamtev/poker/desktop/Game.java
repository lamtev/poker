package com.lamtev.poker.desktop;

import com.lamtev.poker.core.ai.ThinkingAI;
import com.lamtev.poker.core.api.*;
import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.MoveAbility;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
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
import static java.util.stream.Collectors.toSet;
import static javafx.geometry.Orientation.HORIZONTAL;
import static javafx.geometry.Pos.CENTER;

public class Game implements Play {

    private static final ImageView DEALER_BUTTON_VIEW = new ImageView("pics/dealer.png");
    private static final ImageView SMALL_BLIND_BUTTON_VIEW = new ImageView("pics/small-blind.png");
    private static final ImageView BIG_BLIND_BUTTON_VIEW = new ImageView("pics/big-blind.png");
    private static final Image CARD_BACK_IMAGE = new Image("pics/back_side.png");
    private static final double COMMUNITY_CARD_HEIGHT = HEIGHT / 3;
    private static final double COMMUNITY_CARD_WIDTH = COMMUNITY_CARD_HEIGHT / 1.533;
    private static final double HOLE_CARD_HEIGHT = WIDTH / 15;
    private static final double HOLE_CARD_WIDTH = WIDTH / 22.8;
    private static final double DEALER_BUTTON_SIZE = HEIGHT / 12.875;
    private static final double BUTTON_WIDTH = WIDTH / 10;
    private static final double BUTTON_HEIGHT = HEIGHT / 16.48;
    private static final double COMMUNITY_CARDS_SPACING = WIDTH / 51;
    private static final int PLAYER_BUTTON_INDEX = 0;
    private static final int PLAYER_WAGER_INDEX = 1;
    private static final int PLAYER_CARDS_INDEX = 2;
    private static final int PLAYER_NICK_INDEX = 3;
    private static final int PLAYER_STACK_INDEX = 4;
    private static final int PLAYER_CURRENT_OR_FOLD_INDEX = 5;
    private static final int AI_THINKING_TIME_IN_MILLIS = 2000;
    private static final int NEXT_ROUND_DELAY_IN_MILLIS = 2500;
    private static final int EACH_ROUND_TO_INCREASE_BLIND_WAGERS = 5;
    private static final double BLIND_WAGERS_INCREASING_COEFFICIENT = 1.25;
    private static final double PLAYERS_VIEW_HBOX_SPACING = WIDTH / 153;
    private static final int BUTTONS_HBOX_SPACING = 10;
    private static final double ROOT_SPACING = HEIGHT / 33;

    private Stage primaryStage;
    private RoundOfPlay poker;
    private int smallBlindWager;
    private int roundOfPlayCounter = 1;
    private String currentPlayerId;
    private final List<Player> players = new ArrayList<>();
    private final User user;
    private final List<AI> ais = new ArrayList<>();
    private final List<String> nicks;
    private final MoveAbility moveAbility = new MoveAbility();

    private final HBox playersView = new HBox();
    private final HBox communityCardsView = new HBox();
    private final Label statusBarLabel = new Label();
    private final Label bankLabel = new Label();
    private final Button call = new Button("call");
    private final Button fold = new Button("fold");
    private final Button raise = new Button("raise");
    private final Button check = new Button("check");
    private final Button allIn = new Button("all in");
    private final Button showDown = new Button("show down");
    private final HBox buttons = new HBox();

    Game(String userName, List<String> aiNames, int stack) {
        aiNames.forEach(it -> ais.add(new ThinkingAI(it, stack)));
        user = new User(userName, stack);
        players.add(user);
        players.addAll(ais);
        nicks = players.stream().map(Player::id).collect(Collectors.toList());
        smallBlindWager = stack / 500;
    }

    void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        VBox root = new VBox();
        root.setSpacing(ROOT_SPACING);

        VBox statusBarVBox = new VBox();
        statusBarVBox.setAlignment(CENTER);
        statusBarVBox.getChildren().add(statusBarLabel);
        root.getChildren().add(statusBarVBox);

        Separator horizontalSeparator = new Separator(HORIZONTAL);
        horizontalSeparator.setPrefWidth(WIDTH);
        root.getChildren().add(horizontalSeparator);

        communityCardsView.setPrefHeight(COMMUNITY_CARD_HEIGHT);
        communityCardsView.setPrefWidth(WIDTH);
        root.getChildren().add(communityCardsView);

        VBox bankVBox = new VBox();
        bankVBox.setAlignment(CENTER);
        bankVBox.getChildren().add(bankLabel);
        root.getChildren().add(bankVBox);

        root.getChildren().add(playersView);

        buttons.setAlignment(CENTER);
        buttons.setSpacing(BUTTONS_HBOX_SPACING);
        buttons.getChildren().addAll(call, check, raise, allIn, fold, showDown);
        root.getChildren().add(buttons);
        setUpButtons();

        String dealerId = dealerId();
        startNewRound(players, dealerId);

        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
    }

    private void startNewRound(List<Player> players, String dealerId) {
        statusBarLabel.setText("Welcome to Texas Hold'em Poker!!!");
        communityCardsView.getChildren().clear();

        configurePlayersView();

        poker = new PokerBuilder()
                .registerPlayers(players)
                .setDealerId(dealerId)
                .setSmallBlindWager(smallBlindWager)
                .registerPlay(this)
                .create();
    }

    private void configurePlayersView() {
        playersView.getChildren().clear();
        playersView.setAlignment(CENTER);
        playersView.setSpacing(PLAYERS_VIEW_HBOX_SPACING);

        players.forEach(it -> {
            VBox playerView = new VBox(2);
            playerView.setId(it.id());
            playerView.setAlignment(CENTER);

            List<Node> playerViewChildren = playerView.getChildren();
            playerViewChildren.add(PLAYER_BUTTON_INDEX, buttonsHBox(it.id()));
            playerViewChildren.add(PLAYER_WAGER_INDEX, wagerLabel());
            playerViewChildren.add(PLAYER_CARDS_INDEX, cardsHBox());
            playerViewChildren.add(PLAYER_NICK_INDEX, nickLabel(it));
            playerViewChildren.add(PLAYER_STACK_INDEX, stackLabel(it));
            playerViewChildren.add(PLAYER_CURRENT_OR_FOLD_INDEX, currentOrFoldLabel());

            playersView.getChildren().add(playerView);
        });
    }

    private HBox buttonsHBox(String id) {
        HBox buttonsHBox = new HBox(1);
        buttonsHBox.setAlignment(CENTER);
        buttonsHBox.setId("buttons");
        List<ImageView> buttons = new ArrayList<>();
        if (id.equals(dealerId())) {
            buttons.add(DEALER_BUTTON_VIEW);
        } else if (id.equals(smallBlindId())) {
            buttons.add(SMALL_BLIND_BUTTON_VIEW);
        }
        if (nicks.size() > 2 && id.equals(bigBlindId()) ||
                nicks.size() == 2 && id.equals(dealerId())) {
            buttons.add(BIG_BLIND_BUTTON_VIEW);
        }
        if (buttons.size() == 0) {
            buttons.add(new ImageView());
        }
        buttons.forEach(it -> {
            it.setFitWidth(DEALER_BUTTON_SIZE);
            it.setFitHeight(DEALER_BUTTON_SIZE);
        });
        buttonsHBox.getChildren().addAll(buttons);
        return buttonsHBox;
    }

    private Label wagerLabel() {
        Label wagerLabel = new Label("0");
        wagerLabel.setId("wager");
        return wagerLabel;
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

    private Label nickLabel(Player it) {
        Label nickLabel = new Label(it.id());
        nickLabel.setId("nick");
        return nickLabel;
    }

    private Label stackLabel(Player it) {
        Label stackLabel = new Label(Integer.toString(it.stack()));
        stackLabel.setId("stack");
        return stackLabel;
    }

    private Label currentOrFoldLabel() {
        Label currentLabel = new Label();
        currentLabel.setId("current");
        return currentLabel;
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
            } catch (Exception e) {
                statusBarLabel.setText(e.getMessage());
            }
        });
    }

    private void setUpAllInButton() {
        allIn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        allIn.setId("allIn");
        allIn.setOnAction(event -> {
            try {
                poker.allIn();
            } catch (Exception e) {
                statusBarLabel.setText(e.getMessage());
            }
        });
    }

    private void setUpFoldButton() {
        fold.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        fold.setId("fold");
        fold.setOnAction(event -> {
            try {
                poker.fold();
            } catch (Exception e) {
                statusBarLabel.setText(e.getMessage());
            }
        });
    }

    private void setUpCheckButton() {
        check.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        check.setId("check");
        check.setOnAction(event -> {
            try {
                poker.check();
            } catch (Exception e) {
                statusBarLabel.setText(e.getMessage());
            }
        });
    }

    private void setUpShowDownButton() {
        showDown.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        showDown.setId("showDown");
        showDown.setOnAction(event -> {
            try {
                poker.showDown();
            } catch (Exception e) {
                statusBarLabel.setText(e.getMessage());
            }
        });
    }

    private void setUpRaiseButton() {
        raise.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        raise.setId("raise");
        raise.setOnAction(event -> {
            TextInputDialog dialogWindow = new TextInputDialog("" + smallBlindWager * 2);
            dialogWindow.setTitle("Raise");
            dialogWindow.setContentText("Input additional wager:");

            Optional<String> option = dialogWindow.showAndWait();
            option.ifPresent(o -> {
                try {
                    int additionalWager = Integer.parseInt(option.get());
                    if (additionalWager < smallBlindWager * 2) {
                        throw new RuntimeException("Additional wager can't be less than Big Blind wager");
                    }
                    poker.raise(additionalWager);
                } catch (NumberFormatException e) {
                    statusBarLabel.setText("You input not a number");
                } catch (Exception e) {
                    statusBarLabel.setText(e.getMessage());
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
                    Duration.millis(AI_THINKING_TIME_IN_MILLIS),
                    ae -> ai.makeAMove()
            ));
            timeline.play();
        }
    }

    @Override
    public void stateChanged(String stateName) {
        if (stateName.equals("RoundOfPlayIsOverState")) {
            statusBarLabel.setText("Round of play is over!");
            initUser();
            if (onUserHaveLost()) return;
            removeLosers();
            if (onUserHaveWon()) return;
            startNextRound();
        }
    }

    private void initUser() {
        user.setActive(true);
        user.setWager(0);
        user.cards().clear();
    }

    private boolean onUserHaveLost() {
        if (user.stack() == 0) {
            Alert gameIsOverWindow = new Alert(Alert.AlertType.INFORMATION);
            gameIsOverWindow.setTitle("Game is over!!!");
            gameIsOverWindow.setContentText("You have lost all your money!!!");
            gameIsOverWindow.setOnCloseRequest(event -> {
                StartMenu startMenu = new StartMenu();
                startMenu.start(primaryStage);
            });
            gameIsOverWindow.show();
            return true;
        }
        return false;
    }

    private boolean onUserHaveWon() {
        if (players.size() == 1) {
            Alert youWonWindow = new Alert(Alert.AlertType.INFORMATION);
            youWonWindow.setTitle("You won!!!");
            youWonWindow.setContentText("Congratulations!!!");
            youWonWindow.setOnCloseRequest(event -> {
                StartMenu sm = new StartMenu();
                sm.start(primaryStage);
            });
            youWonWindow.show();
            return true;
        }
        return false;
    }

    private void removeLosers() {
        Set<String> losers = players.stream()
                .filter(it -> it.stack() <= 0)
                .map(Player::id)
                .collect(toSet());
        players.removeIf(it -> losers.contains(it.id()));
        nicks.removeIf(losers::contains);
    }

    private void startNextRound() {
        if (++roundOfPlayCounter % EACH_ROUND_TO_INCREASE_BLIND_WAGERS == 0) {
            smallBlindWager *= BLIND_WAGERS_INCREASING_COEFFICIENT;
        }
        Collections.rotate(nicks, -1);
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(NEXT_ROUND_DELAY_IN_MILLIS),
                ae -> startNewRound(players, dealerId())
        ));
        timeline.play();
    }

    @Override
    public void playerFold(String playerId) {
        statusBarLabel.setText(playerId + " fold");
        if (user.id().equals(playerId)) {
            user.setActive(false);
        }
        VBox playerView = playerView(playerId);
        playerView.getChildren().get(PLAYER_CARDS_INDEX).setVisible(false);
        Label currentLabel = (Label) playerView.getChildren().get(PLAYER_CURRENT_OR_FOLD_INDEX);
        currentLabel.setText("fold");
    }

    @Override
    public void playerMoneyUpdated(String playerId, int playerStack, int playerWager) {
        if (user.id().equals(playerId)) {
            user.setStack(playerStack);
            user.setWager(playerWager);
        }
        VBox playerView = playerView(playerId);
        playerView.getChildren().set(PLAYER_WAGER_INDEX, new Label(Integer.toString(playerWager)));
        playerView.getChildren().set(PLAYER_STACK_INDEX, new Label(Integer.toString(playerStack)));
    }

    @Override
    public void currentPlayerChanged(String currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
        playersView.getChildren().forEach(it -> {
            Label currentLabel = (Label) ((VBox) it).getChildren().get(PLAYER_CURRENT_OR_FOLD_INDEX);
            if (it.getId().equals(currentPlayerId)) {
                currentLabel.setText("current");
            } else {
                currentLabel.setText("fold".equals(currentLabel.getText()) ? "fold" : "");
            }
        });
        if (user.id().equals(currentPlayerId)) {
            updateButtonsAbility();
        } else {
            buttons.getChildren().forEach(it -> it.setDisable(true));
            makeAMoveOfAI();
        }
    }

    private void updateButtonsAbility() {
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
    }

    @Override
    public void communityCardsDealt(List<Card> addedCommunityCards) {
        communityCardsView.setAlignment(CENTER);
        communityCardsView.setSpacing(COMMUNITY_CARDS_SPACING);
        addedCommunityCards.forEach(card -> {
            ImageView cardView = new ImageView("pics/" + card.toString() + ".png");
            cardView.setFitHeight(COMMUNITY_CARD_HEIGHT);
            cardView.setFitWidth(COMMUNITY_CARD_WIDTH);
            communityCardsView.getChildren().add(cardView);
        });
    }

    @Override
    public void bankMoneyUpdated(int money, int wager) {
        bankLabel.setText("Bank: " + money);
    }

    @Override
    public void blindWagersPlaced() {

    }

    @Override
    public void holeCardsDealt(Map<String, List<Card>> playerIdToCards) {
        user.setCards(playerIdToCards.get(user.id()));
        playersView.getChildren().forEach(it -> {
            VBox playerView = (VBox) it;
            HBox cards = (HBox) playerView.getChildren().get(PLAYER_CARDS_INDEX);
            if (it.getId().equals(user.id())) {
                showHoleCards(cards, user.cards());
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
        statusBarLabel.setText(playerId + " made all in");
    }

    @Override
    public void playerCalled(String playerId) {
        statusBarLabel.setText(playerId + " called");
    }

    @Override
    public void playerChecked(String playerId) {
        statusBarLabel.setText(playerId + " checked");
    }

    @Override
    public void playerRaised(String playerId) {
        statusBarLabel.setText(playerId + " raised");
    }

    @Override
    public void playerShowedDown(String playerId, PokerHand hand) {
        String handStr = hand.getName().toString().toLowerCase();
        statusBarLabel.setText(playerId + " showed down: " +
                handStr.replaceAll("_", " ")
                        .replaceFirst(handStr.substring(0, 1), handStr.substring(0, 1)
                                .toUpperCase()));
        HBox cardsHBox = (HBox) playerView(playerId).getChildren().get(PLAYER_CARDS_INDEX);
        List<Card> cards = players.stream()
                .filter(it -> it.id().equals(playerId))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .cards();
        showHoleCards(cardsHBox, cards);
    }

    private void showHoleCards(HBox cardsHBox, List<Card> cards) {
        int i = 0;
        for (Card card : cards) {
            ImageView imageView = (ImageView) cardsHBox.getChildren().get(i);
            imageView.setImage(new Image("pics/" + card.toString() + ".png"));
            ++i;
        }
    }

    private VBox playerView(String playerId) {
        return (VBox) playersView.getChildren().stream()
                .filter(it -> it.getId().equals(playerId))
                .findFirst()
                .orElseThrow(RuntimeException::new);
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

    private String dealerId() {
        return nicks.get(0);
    }

    private String smallBlindId() {
        return nicks.get(1);
    }

    private String bigBlindId() {
        return nicks.get(2);
    }

}