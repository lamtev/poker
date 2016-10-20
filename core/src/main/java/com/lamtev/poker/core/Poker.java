package com.lamtev.poker.core;

import java.util.ArrayList;
import static com.lamtev.poker.core.GameStage.*;

public class Poker implements PokerAPI {

    private int numberOfPlayers;
    private int smallBlindSize;
    private int bigBlindSize;
    private int bank;
    private ArrayList<Player> players;
    private ArrayList<Player> activePlayers;
    private ArrayList<Integer> activePlayersWagers;
    private Player smallBlind;
    private Player bigBlind;
    private int smallBlindIndex;
    private int bigBlindIndex;
    private int currentWager;
    private int currentPlayerIndex;
    private Dealer dealer;
    private GameStage currentGameStage;
    private int raisesInLap;
    int moves;

    public Poker(int numberOfPlayers, int smallBlindSize, int playerStack) {
        this.numberOfPlayers = numberOfPlayers;
        this.smallBlindSize = smallBlindSize;
        this.bigBlindSize = 2 * smallBlindSize;

        players = new ArrayList<>(numberOfPlayers);
        players.forEach((x) -> x.takeMoney(playerStack));
        dealer = new Dealer(players);
        activePlayersWagers = new ArrayList<>(numberOfPlayers);
        smallBlindIndex = -1;
        bigBlindIndex = 0;
        bank = 0;
        currentPlayerIndex = 2;
        currentGameStage = BLINDS;
        raisesInLap = 0;
        moves = 0;
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

    public void  call() {
        //TODO validate permissions
        ++moves;
        bank += activePlayers.get(currentPlayerIndex).giveMoney(currentWager);
        if (moves < activePlayers.size()) {
            activePlayersWagers.add(currentWager);
        } else {
            activePlayersWagers.set(currentPlayerIndex, currentWager);
        }
        ++currentPlayerIndex;
        currentPlayerIndex %= activePlayers.size();
        if (isEndOfLap()) {
            currentGameStage = currentGameStage.next();
        }
        setBlinds();
        makeDealerJob();
    }

    private boolean isEndOfLap() {
        //TODO
        for (Integer x : activePlayersWagers) {
            if (!x.equals(currentWager)) {
                return false;
            }
        }

        return true;
    }

    public void raise(int additionalWager) {
        //TODO validate permissions
        if (!(activePlayers.size() == 2 || raisesInLap < 3)) {
            //TODO
        }
        ++moves;
        ++raisesInLap;
        currentWager += additionalWager;
        bank += activePlayers.get(currentPlayerIndex).giveMoney(currentWager);
        ++currentPlayerIndex;
        currentPlayerIndex %= activePlayers.size();
    }


    //TODO activePlayersWagers

    public void fold() {
        //TODO
        ++moves;
        activePlayers.remove(currentPlayerIndex);
        ++currentPlayerIndex;
        currentPlayerIndex %= activePlayers.size();
    }
    public void check() {
        //TODO
        if (!activePlayersWagers.get(currentPlayerIndex).equals(currentWager)) {
            //TODO
        }
        ++moves;
        ++currentPlayerIndex;
        if (isEndOfLap()) {
            currentGameStage = currentGameStage.next();
        }
        setBlinds();
        makeDealerJob();
    }

    private void makeDealerJob() {
        switch (currentGameStage) {
            case PREFLOP:
                dealer.makePreflop();
                currentGameStage = FIRST_WAGERING_LAP;
                break;
            case FLOP:
                dealer.makeFlop();
                currentGameStage = SECOND_WAGERING_LAP;
                break;
            case TURN:
                dealer.makeTurn();
                currentGameStage = THIRD_WAGERING_LAP;
                break;
            case RIVER:
                dealer.makeRiver();
                currentGameStage = FOURTH_WAGERING_LAP;
                break;
            default:
                break;
        }
    }

    private void setBlinds() {
        if (currentGameStage.equals(BLINDS)) {
            raisesInLap = 0;
            activePlayers = players;
            ++smallBlindIndex;
            ++bigBlindIndex;
            smallBlind = activePlayers.get(smallBlindIndex);
            bigBlind = activePlayers.get(bigBlindIndex);
            bank += smallBlind.giveMoney(smallBlindSize);
            bank += bigBlind.giveMoney(bigBlindSize);
            moves = 2;
            //TODO
            activePlayersWagers.clear();
            activePlayersWagers.add(smallBlindSize);
            activePlayersWagers.add(bigBlindSize);
            currentWager = bigBlindSize;
            currentGameStage = PREFLOP;
            //TEMPORARY solution
            players.forEach((x) -> x.takeMoney(bank / activePlayers.size()));
            bank = 0;
        }
    }

}
