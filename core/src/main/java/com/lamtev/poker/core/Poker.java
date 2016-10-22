package com.lamtev.poker.core;

import java.util.ArrayList;
import java.util.List;

import static com.lamtev.poker.core.GameStage.*;

public class Poker implements PokerAPI {

    private int numberOfPlayers;
    private int smallBlindSize;
    private int bigBlindSize;
    private int bank;
    private List<Player> players;
    private List<Player> activePlayers;
    private List<Integer> activePlayersWagers;
    private Player smallBlind;
    private Player bigBlind;
    private int smallBlindIndex;
    private int bigBlindIndex;
    private int currentWager;
    private int currentPlayerIndex;
    private Dealer dealer;
    private GameStage currentGameStage;
    private int raisesInWageringLap;
    private int moves;
    private int movesInWageringLap;
    private boolean isGameOver;

    public Poker(int numberOfPlayers, int smallBlindSize, int playerStack) {
        this.numberOfPlayers = numberOfPlayers;
        this.smallBlindSize = smallBlindSize;
        this.bigBlindSize = 2 * smallBlindSize;

        players = new ArrayList<Player>() {{
            for (int i = 0; i < numberOfPlayers; ++i) {
                add(new Player(playerStack));
            }
        }};
        dealer = new Dealer(players);
        activePlayersWagers = new ArrayList<>();
        smallBlindIndex = -1;
        bigBlindIndex = 0;
        bank = 0;
        currentPlayerIndex = 2;
        currentGameStage = BLINDS;
        raisesInWageringLap = 0;
        moves = 0;
        isGameOver = false;
        setBlinds();
        makeDealerJob();
    }

    public int getMoves() {
        return moves;
    }

    public int getBank() {
        return bank;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public int getSmallBlindSize() {
        return smallBlindSize;
    }

    public int getBigBlindSize() {
        return bigBlindSize;
    }

    public int getSmallBlindIndex() {
        return smallBlindIndex;
    }

    public int getBigBlindIndex() {
        return bigBlindIndex;
    }

    public int getCurrentWager() {
        return currentWager;
    }

    public GameStage getCurrentGameStage() {
        return currentGameStage;
    }

    public int getRaisesInWageringLap() {
        return raisesInWageringLap;
    }

    public void call() {
        validateCall();
        bank += activePlayers.get(currentPlayerIndex).giveMoney(currentWager);
        updateActivePlayersWagers();
        ++moves;
        ++movesInWageringLap;
        changeCurrentIndex();
        finishWageringLap();
    }

    public void raise(int additionalWager) {
        validateRaise();
        currentWager += additionalWager;
        bank += activePlayers.get(currentPlayerIndex).giveMoney(currentWager-activePlayersWagers.get(currentPlayerIndex));
        updateActivePlayersWagers();
        ++raisesInWageringLap;
        ++moves;
        ++movesInWageringLap;
        changeCurrentIndex();
    }

    public void fold() {
        validateFold();
        activePlayers.get(currentPlayerIndex).fold();
        System.out.println(activePlayers.get(currentPlayerIndex).cards().size());
        activePlayers.remove(currentPlayerIndex);
        activePlayersWagers.remove(currentPlayerIndex);
        ++moves;
        ++movesInWageringLap;
        finishWageringLap();
    }

    public void check() {
        validateCheck();
        ++moves;
        ++movesInWageringLap;
        changeCurrentIndex();
        finishWageringLap();
    }

    private void validateCall() {
        if (isGameOver ||
                activePlayersWagers.size() > currentPlayerIndex &&
                        activePlayersWagers.get(currentPlayerIndex) == currentWager) {
            String message = "You can't call because " +
                    (isGameOver ? "game is over!" : "your wager equals to current wager!");

            throw new RuntimeException(message);
        }
    }

    private void validateRaise() {
        if (isGameOver || activePlayers.size() != 2 && raisesInWageringLap >= 3) {
            String message = "You can't raise because " +
                    (isGameOver ? "game is over!" : "there are already 3 raises per lap!");

            throw new RuntimeException(message);
        }
    }

    private void validateCheck() {
        if (isGameOver || activePlayersWagers.size() <= currentPlayerIndex ||
                activePlayersWagers.get(currentPlayerIndex) != currentWager) {
            String message = "You can't check because " +
                    (isGameOver ? "game is over!" : "your wager not equals to current wager!");

            throw new RuntimeException(message);
        }
    }

    private void validateFold() {
        if (isGameOver) {
            throw new RuntimeException("You can't fold because game is over!");
        }
    }

    private void updateActivePlayersWagers() {
        if (moves < activePlayers.size()) {
            activePlayersWagers.add(currentWager);
        } else {
            activePlayersWagers.set(currentPlayerIndex, currentWager);
        }
    }

    private void changeCurrentIndex() {
        ++currentPlayerIndex;
        currentPlayerIndex %= activePlayers.size();
    }

    private void finishWageringLap() {
        if (isEndOfLap()) {
            currentGameStage = currentGameStage.next();
            setBlinds();
            makeDealerJob();
            //TODO determine winner
        }
    }

    private boolean isEndOfLap() {
        for (Integer x : activePlayersWagers) {
            if (x != currentWager) {
                return false;
            }
        }

        return movesInWageringLap >= activePlayers.size();
    }

    private void makeDealerJob() {
        switch (currentGameStage) {
            case PREFLOP:
                dealer.makePreflop();
                currentGameStage = FIRST_WAGERING_LAP;
                raisesInWageringLap = movesInWageringLap = 0;
                break;
            case FLOP:
                dealer.makeFlop();
                currentGameStage = SECOND_WAGERING_LAP;
                raisesInWageringLap = movesInWageringLap = 0;
                break;
            case TURN:
                dealer.makeTurn();
                currentGameStage = THIRD_WAGERING_LAP;
                raisesInWageringLap = movesInWageringLap = 0;
                break;
            case RIVER:
                dealer.makeRiver();
                currentGameStage = FOURTH_WAGERING_LAP;
                raisesInWageringLap = movesInWageringLap = 0;
                break;
            default:
                break;
        }
    }

    private void setBlinds() {
        if (currentGameStage.equals(BLINDS)) {
            activePlayers = players;
            activePlayersWagers.clear();
            bank = 0;
            ++smallBlindIndex;
            ++bigBlindIndex;
            smallBlind = activePlayers.get(smallBlindIndex);
            bigBlind = activePlayers.get(bigBlindIndex);
            bank += smallBlind.giveMoney(smallBlindSize);
            bank += bigBlind.giveMoney(bigBlindSize);
            activePlayersWagers.add(smallBlindSize);
            activePlayersWagers.add(bigBlindSize);
            moves = 2;
            movesInWageringLap = 2;
            currentWager = bigBlindSize;
            currentGameStage = currentGameStage.next();
            //TEMPORARY solution
            //When winnerDetermination will be implemented, line below will be replaced by
            //giving bank to winner
            players.forEach((x) -> x.takeMoney(bank / activePlayers.size()));
        }
    }

}
