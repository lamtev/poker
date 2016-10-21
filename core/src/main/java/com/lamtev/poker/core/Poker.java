package com.lamtev.poker.core;

import java.util.ArrayList;
import java.util.List;

import static com.lamtev.poker.core.GameStage.*;
import static java.lang.System.gc;

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
    private int raisesInLap;
    private int moves;
    private int movesInLap;
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
        raisesInLap = 0;
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

    public int getRaisesInLap() {
        return raisesInLap;
    }

    public void call() {
        if (isGameOver ||
                activePlayersWagers.size() > currentPlayerIndex &&
                activePlayersWagers.get(currentPlayerIndex) == currentWager) {
            //TODO normal exception
            throw new RuntimeException(" You can't call");
        }

        bank += activePlayers.get(currentPlayerIndex).giveMoney(currentWager);
        //TODO verify the condition
        updateActivePlayersWagers();
        ++moves;
        ++movesInLap;
        changeCurrentIndex();
        //System.out.println(activePlayersWagers.get(0) + " " + activePlayersWagers.get(1) + " " + activePlayersWagers.get(2) + " " + currentWager);
        System.out.println(moves);
        System.out.println(isEndOfLap());
        System.out.println(movesInLap + " " + activePlayers.size() + " " + activePlayers.size());
        System.out.println();
        if (isEndOfLap()) {
            currentGameStage = currentGameStage.next();
            movesInLap = 2;
        }
        //TODO determine winner
        setBlinds();
        makeDealerJob();
    }

    private void updateActivePlayersWagers() {
        if (moves < activePlayers.size()) {
            activePlayersWagers.add(currentWager);

        } else {
            activePlayersWagers.set(currentPlayerIndex, currentWager);
        }
    }

    public void raise(int additionalWager) {
        if (isGameOver || activePlayers.size() != 2 && raisesInLap >= 3) {
            //TODO normal exception
            System.out.println(activePlayers.size());
            System.out.println(raisesInLap);
            throw new RuntimeException();
        }
        currentWager += additionalWager;
        bank += activePlayers.get(currentPlayerIndex).giveMoney(currentWager-activePlayersWagers.get(currentPlayerIndex));
        updateActivePlayersWagers();
        ++raisesInLap;
        ++moves;
        ++movesInLap;
        changeCurrentIndex();
    }

    //TODO activePlayersWagers

    public void fold() {
        if (isGameOver) {
            //TODO normal exception
            throw new RuntimeException();
        }
        activePlayers.get(currentPlayerIndex).fold();
        System.out.println(activePlayers.get(currentPlayerIndex).cards().size());
        activePlayers.remove(currentPlayerIndex);
        activePlayersWagers.remove(currentPlayerIndex);
        ++moves;
        //changeCurrentIndex();
        //TODO determine winner
    }

    public void check() {
        //TODO
        if (activePlayersWagers.size() <= currentPlayerIndex || activePlayersWagers.get(currentPlayerIndex) != currentWager) {
            throw new RuntimeException(String.valueOf(activePlayersWagers.size() <= currentPlayerIndex) + (activePlayersWagers.get(currentPlayerIndex) != currentWager));
        }
        ++moves;
        ++movesInLap;
        changeCurrentIndex();
        System.out.println(moves);
        System.out.println(isEndOfLap());
        System.out.println(movesInLap + " " + activePlayers.size() + " " + activePlayers.size());
        System.out.println();
        if (isEndOfLap()) {
            currentGameStage = currentGameStage.next();
            movesInLap = 2;
        }
        setBlinds();
        makeDealerJob();
        //TODO determine winner
    }

    private boolean isEndOfLap() {
        for (Integer x : activePlayersWagers) {
            if (x != currentWager) {
                return false;
            }
        }

        return movesInLap >= activePlayers.size();
    }

    private void changeCurrentIndex() {
        ++currentPlayerIndex;
        currentPlayerIndex %= activePlayers.size();
    }

    private void makeDealerJob() {
        switch (currentGameStage) {
            case PREFLOP:
                dealer.makePreflop();
                currentGameStage = FIRST_WAGERING_LAP;
                raisesInLap = 0;
                break;
            case FLOP:
                dealer.makeFlop();
                currentGameStage = SECOND_WAGERING_LAP;
                raisesInLap = 0;
                break;
            case TURN:
                dealer.makeTurn();
                currentGameStage = THIRD_WAGERING_LAP;
                raisesInLap = 0;
                break;
            case RIVER:
                dealer.makeRiver();
                currentGameStage = FOURTH_WAGERING_LAP;
                raisesInLap = 0;
                break;
            default:
                break;
        }
    }

    private void setBlinds() {
        if (currentGameStage.equals(BLINDS)) {
            activePlayers = players;
            activePlayersWagers.clear();
//            for (int i = 0; i < activePlayers.size(); ++i) {
//                activePlayersWagers.add(0);
//            }
            bank = 0;
            ++smallBlindIndex;
            ++bigBlindIndex;
            smallBlind = activePlayers.get(smallBlindIndex);
            bigBlind = activePlayers.get(bigBlindIndex);
            bank += smallBlind.giveMoney(smallBlindSize);
            bank += bigBlind.giveMoney(bigBlindSize);
            moves = 2;
            movesInLap = 2;
            //TODO

            activePlayersWagers.add(smallBlindSize);
            activePlayersWagers.add(bigBlindSize);
            currentWager = bigBlindSize;
            currentGameStage = PREFLOP;
            //TEMPORARY solution
            //When winnerDetermination will be implemented, line below will be replaced by
            //giving bank to winner
            players.forEach((x) -> x.takeMoney(bank / activePlayers.size()));
        }
    }

}
