package com.lamtev.poker.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.lamtev.poker.core.GameStage.*;

public class Poker implements PokerAPI {

    private final static Map<GameStage, GameStage> NEXT_GAME_STAGE = new HashMap<GameStage, GameStage>() {{
        put(BLINDS, PREFLOP);
        put(PREFLOP, FIRST_BETTING_LAP);
        put(FIRST_BETTING_LAP, FLOP);
        put(FLOP, SECOND_BETTING_LAP);
        put(SECOND_BETTING_LAP, TURN);
        put(TURN, THIRD_BETTING_LAP);
        put(THIRD_BETTING_LAP, RIVER);
        put(RIVER, FOURTH_BETTING_LAP);
        put(FOURTH_BETTING_LAP, BLINDS);
    }};

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

    public Poker(int numberOfPlayers, int smallBlindSize, int playerStack) {
        this.numberOfPlayers = numberOfPlayers;
        this.smallBlindSize = smallBlindSize;
        this.bigBlindSize = 2 * smallBlindSize;

        players = new ArrayList<>(numberOfPlayers);
        players.forEach((x) -> x.takeMoney(playerStack));
        dealer = new Dealer(players);
        activePlayersWagers = new ArrayList<>(numberOfPlayers);
        smallBlindIndex = 0;
        bigBlindIndex = 1;
        bank = 0;
        currentPlayerIndex = 2;
        currentGameStage = BLINDS;
        raisesInLap = 0;
        setBlinds();
        makeDealerJob();
    }

    public void  call() {
        //TODO validate permissions
        bank += activePlayers.get(currentPlayerIndex).giveMoney(currentWager);
        ++currentPlayerIndex;
        currentPlayerIndex %= activePlayers.size();
        if (isEndOfLap()) {
            currentGameStage = NEXT_GAME_STAGE.get(currentGameStage);
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
        ++raisesInLap;
        currentWager += additionalWager;
        bank += activePlayers.get(currentPlayerIndex).giveMoney(currentWager);
        ++currentPlayerIndex;
        currentPlayerIndex %= activePlayers.size();
    }


    //TODO activePlayersWagers

    public void fold() {
        //TODO
        activePlayers.remove(currentPlayerIndex);
        ++currentPlayerIndex;
        currentPlayerIndex %= activePlayers.size();
    }
    public void check() {
        //TODO
    }

    private void makeDealerJob() {
        switch (currentGameStage) {
            case PREFLOP:
                dealer.makePreflop();
                currentGameStage = FIRST_BETTING_LAP;
                break;
            case FLOP:
                dealer.makeFlop();
                currentGameStage = SECOND_BETTING_LAP;
                break;
            case TURN:
                dealer.makeTurn();
                currentGameStage = THIRD_BETTING_LAP;
                break;
            case RIVER:
                dealer.makeRiver();
                currentGameStage = FOURTH_BETTING_LAP;
                break;
            default:
                break;
        }
    }

    private void setBlinds() {
        if (currentGameStage.equals(BLINDS)) {
            raisesInLap = 0;
            activePlayers = players;
            smallBlind = activePlayers.get(smallBlindIndex);
            bigBlind = activePlayers.get(bigBlindIndex);
            bank += smallBlind.giveMoney(smallBlindSize);
            bank += bigBlind.giveMoney(bigBlindSize);
            //TODO
            activePlayersWagers.add(smallBlindSize);
            activePlayersWagers.add(bigBlindSize);
            currentWager = bigBlindSize;
            currentGameStage = PREFLOP;
        }
    }

}
