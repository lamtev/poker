package com.lamtev.poker.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.lamtev.poker.core.GameStage.*;


// Не нравится мне этот класс, уж очень много полей

//согласен
public class Poker implements PokerAPI {

    private int smallBlindSize;
    private int bigBlindSize;
    private Map<String, Player> players;
    private List<Player> activePlayers;
    private List<Integer> activePlayersWagers;
    private int smallBlindIndex;
    private int bigBlindIndex;
    private int currentWager;
    private int currentPlayerIndex;
    private Bank bank;
    private Dealer dealer;
    private GameStage currentGameStage;
    private int raisesInWageringLap;
    private int moves;
    private int movesInWageringLap;
    private boolean isGameOver;

    public Poker(Map<String, Player> players, int smallBlindSize) throws Exception {
        this.smallBlindSize = smallBlindSize;
        this.bigBlindSize = 2 * smallBlindSize;

        this.players = players;
        dealer = new Dealer(new ArrayList<>(this.players.values()));
        activePlayersWagers = new ArrayList<>();
        smallBlindIndex = -1;
        bigBlindIndex = 0;
        currentPlayerIndex = 2;
        currentGameStage = BLINDS;
        raisesInWageringLap = 0;
        moves = 0;
        isGameOver = false;
        setBlinds();
        makeDealerJob();
    }

    @Override
    public void start(List<Object> listWithPlayersInfo, int smallBlindSize) {
        //TODO implement it
    }

    @Override
    public int getPlayerWager(String playerID) {
        //TODO implement it
        return 0;
    }

    @Override
    public int getPlayerStack(String playerID) {
        //TODO implement it
        return 0;
    }

    @Override
    public Cards getPlayerCards(String playerID) {
        //TODO implement it
        return null;
    }

    @Override
    public Cards getCommonCards() {
        //TODO implement it
        return null;
    }

    @Override
    public int getMoneyInBank() {
        return bank.getMoney();
    }



    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }


    public int getCurrentWager() {
        return currentWager;
    }

    public GameStage getCurrentGameStage() {
        return currentGameStage;
    }

    @Override
    public void call() throws Exception {
        validateCall();
        bank.takeMoneyFromPlayer(currentWager, currentPlayerIndex);
        updateActivePlayersWagers();
        ++moves;
        ++movesInWageringLap;
        changeCurrentIndex();
        finishWageringLap();
    }

    @Override
    public void raise(int additionalWager) throws Exception {
        validateRaise();
        currentWager += additionalWager;
        bank.takeMoneyFromPlayer(currentWager-activePlayersWagers.get(currentPlayerIndex), currentPlayerIndex);
        updateActivePlayersWagers();
        ++raisesInWageringLap;
        ++moves;
        ++movesInWageringLap;
        changeCurrentIndex();
    }

    @Override
    public void fold() throws Exception {
        validateFold();
        activePlayers.get(currentPlayerIndex).fold();
        System.out.println(activePlayers.get(currentPlayerIndex).getCards().size());
        activePlayers.remove(currentPlayerIndex);
        activePlayersWagers.remove(currentPlayerIndex);
        ++moves;
        ++movesInWageringLap;
        finishWageringLap();
    }

    @Override
    public void check() throws Exception {
        validateCheck();
        ++moves;
        ++movesInWageringLap;
        changeCurrentIndex();
        finishWageringLap();
    }

    private void validateCall() throws Exception {
        if (isGameOver ||
                activePlayersWagers.size() > currentPlayerIndex &&
                        activePlayersWagers.get(currentPlayerIndex) == currentWager) {
            String message = "You can't call because " +
                    (isGameOver ? "game is over!" : "your wager equals to current wager!");

            throw new Exception(message);
        }
    }

    private void validateRaise() throws Exception {
        if (isGameOver || activePlayers.size() != 2 && raisesInWageringLap >= 3) {
            String message = "You can't raise because " +
                    (isGameOver ? "game is over!" : "there are already 3 raises per lap!");

            throw new Exception(message);
        }
    }

    private void validateCheck() throws Exception {
        if (isGameOver || activePlayersWagers.size() <= currentPlayerIndex ||
                activePlayersWagers.get(currentPlayerIndex) != currentWager) {
            String message = "You can't check because " +
                    (isGameOver ? "game is over!" : "your wager not equals to current wager!");

            throw new Exception(message);
        }
    }

    private void validateFold() throws Exception {
        if (isGameOver) {
            throw new Exception("You can't fold because game is over!");
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

    private void finishWageringLap() throws Exception {
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

    private void makeDealerJob() throws Exception {
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
            activePlayers = new ArrayList<>(players.values());
            activePlayersWagers.clear();
            bank = new Bank(players);
            ++smallBlindIndex;
            ++bigBlindIndex;
            bank.takeMoneyFromPlayer(smallBlindSize, smallBlindIndex);
            bank.takeMoneyFromPlayer(bigBlindSize, bigBlindIndex);
            activePlayersWagers.add(smallBlindSize);
            activePlayersWagers.add(bigBlindSize);
            moves = 2;
            movesInWageringLap = 2;
            currentWager = bigBlindSize;
            currentGameStage = currentGameStage.next();
            //TEMPORARY solution
            //When winnerDetermination will be implemented, line below will be replaced by
            //giving bank to winner
            int i = 0;
            for (Player player : players.values()) {
                bank.giveMoneyToPlayer(bank.getMoney() / activePlayers.size(), i++);
            }
        }
    }

}
