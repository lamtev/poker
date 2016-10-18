package com.lamtev.poker.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.lamtev.poker.core.GameStage.*;

public class Poker implements PokerAPI {

    private final static Map<GameStage, GameStage> NEXT_GAME_STAGE = new HashMap<GameStage, GameStage>(){{
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
    private Player smallBlind;
    private Player bigBlind;
    private int smallBlindIndex;
    private int bigBlindIndex;
    private int currentWager;
    private int currentPlayerIndex;
    private Dealer dealer;
    private GameStage currentGameStage;

    public Poker(int numberOfPlayers, int smallBlindSize, int playerStack) {
        this.numberOfPlayers = numberOfPlayers;
        this.smallBlindSize = smallBlindSize;
        this.bigBlindSize = 2 * smallBlindSize;

        players = new ArrayList<>(numberOfPlayers);
        players.forEach((x) -> x.takeMoney(playerStack));
        dealer = new Dealer(players);
        smallBlindIndex = 0;
        bigBlindIndex = 1;
        bank = 0;
        currentPlayerIndex = 2;
        currentGameStage = BLINDS;
        setBlinds();
        makeDealerJob();
    }

    public void  call() {
        //TODO validate permissions
        bank += activePlayers.get(currentPlayerIndex).giveMoney(currentWager);
        ++currentPlayerIndex;
        currentPlayerIndex %= activePlayers.size();
        if (true/* end of lap*/) {
            //TODO change stage
            currentGameStage = NEXT_GAME_STAGE.get(currentGameStage);
        }
        setBlinds();
        makeDealerJob();
    }

    public void raise(int additionalWager) {
        //TODO
    }

    public void fold() {
        //TODO
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
            activePlayers = players;
            smallBlind = activePlayers.get(smallBlindIndex);
            bigBlind = activePlayers.get(bigBlindIndex);
            bank += smallBlind.giveMoney(smallBlindSize);
            bank += bigBlind.giveMoney(bigBlindSize);
            currentWager = bigBlindSize;
            currentGameStage = PREFLOP;
        }
    }

}
