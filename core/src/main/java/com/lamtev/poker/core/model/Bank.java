package com.lamtev.poker.core.model;

import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.states.exceptions.IsNotEnoughMoneyException;
import com.lamtev.poker.core.states.exceptions.NotPositiveWagerException;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public final class Bank {

    private int money;
    private int wager;
    private final Players players;
    private final Queue<Pot> pots = new ArrayDeque<>();

    public Bank(Players players) {
        this.players = players;
    }

    public int money() {
        return money;
    }

    public int wager() {
        return wager;
    }

    public void acceptBlindWagers(int smallBlindSize) {
        acceptBlindWager(players.smallBlind(), smallBlindSize);
        acceptBlindWager(players.bigBlind(), smallBlindSize * 2);
    }

    public void acceptCall(Player player) throws IsNotEnoughMoneyException {
        int moneyTakingFromPlayer = wager - player.wager();
        validateTakingMoneyFromPlayer(player, moneyTakingFromPlayer);
        money += player.takeMoney(moneyTakingFromPlayer);
    }

    public void acceptRaise(int additionalWager, Player player) throws
            IsNotEnoughMoneyException, NotPositiveWagerException {
        if (additionalWager <= 0) {
            throw new NotPositiveWagerException();
        }
        int moneyTakingFromPlayer = wager + additionalWager - player.wager();
        validateTakingMoneyFromPlayer(player, moneyTakingFromPlayer);
        money += player.takeMoney(moneyTakingFromPlayer);
        wager += additionalWager;
    }

    public void acceptAllIn(Player player) {
        money += player.takeMoney(player.stack());
        if (player.wager() > wager) {
            wager = player.wager();
        }
    }

    public Set<Player> giveMoneyToWinners(Map<Player, PokerHand> showedDownPlayers) {
        List<Player> showedDownPlayersList = new ArrayList<>(showedDownPlayers.keySet());
        buildUpPots(showedDownPlayersList);
        Set<Player> winners = new HashSet<>();
        while (!pots.isEmpty()) {
            if (money < 0.5) {
                break;
            }
            Pot pot = pots.poll();
            PokerHand bestHand = determineBestHand(showedDownPlayers, pot);
            List<Player> potWinners = pot.applicants.stream()
                    .filter(player -> showedDownPlayers.get(player).equals(bestHand))
                    .collect(Collectors.toList());
            potWinners.forEach(player -> {
                player.addMoney(pot.money / potWinners.size());
                winners.add(player);
            });
            money -= pot.money;
        }
        assert money < 0.5;
        money = 0;
        wager = 0;
        return winners;
    }

    public void giveMoneyToSingleWinner(Player winner) {
        winner.addMoney(money);
        money = 0;
    }

    private void acceptBlindWager(Player blind, int wager) {
        if (blind.stack() <= wager) {
            acceptAllIn(blind);
        } else {
            money += blind.takeMoney(wager);
            this.wager = wager;
        }
    }

    private void validateTakingMoneyFromPlayer(Player player, int moneyTakingFromPlayer)
            throws IsNotEnoughMoneyException {
        if (player.stack() < moneyTakingFromPlayer) {
            throw new IsNotEnoughMoneyException("Try to make allIn" +
                    "(" + player + ", moneyTakingFromHim=" + moneyTakingFromPlayer + ")");
        }
    }

    private void buildUpPots(List<Player> showedDownPlayersList) {
        List<Player> allinners = orderedAllinners(showedDownPlayersList);
        int foldPlayersWagers = calculateFoldPlayersWagers();
        int previousAllinnerWager = 0;
        Set<Player> allinnersWhichAlreadyInPot = new HashSet<>();
        for (Player allinner : allinners) {
            Pot pot = new Pot();
            pot.addApplicantsFrom(showedDownPlayersList, allinnersWhichAlreadyInPot);
            pot.money = foldPlayersWagers + (allinner.wager() - previousAllinnerWager) * pot.applicants.size();
            allinnersWhichAlreadyInPot.add(allinner);
            pots.offer(pot);
            previousAllinnerWager = allinner.wager();
        }
        Pot pot = new Pot();
        pot.addApplicantsFrom(showedDownPlayersList, allinnersWhichAlreadyInPot);
        pot.money = allinners.isEmpty() ?
                foldPlayersWagers + wager * pot.applicants.size() :
                (wager - previousAllinnerWager) * pot.applicants.size();

        pots.offer(pot);
    }

    private PokerHand determineBestHand(Map<Player, PokerHand> showedDownPlayers, Pot pot) {
        return showedDownPlayers.get(
                Collections.max(
                        pot.applicants,
                        Comparator.comparing(showedDownPlayers::get)
                )
        );
    }

    private int calculateFoldPlayersWagers() {
        List<Player> players = new ArrayList<>();
        this.players.forEach(players::add);
        return players.stream()
                .filter(Player::hadFold)
                .map(Player::wager)
                .mapToInt(Number::intValue)
                .sum();
    }

    private List<Player> orderedAllinners(List<Player> showedDownPlayersList) {
        return showedDownPlayersList.stream()
                .filter(Player::isAllinner)
                .sorted(Comparator.comparingInt(Player::wager))
                .collect(toList());
    }

    private static class Pot {
        private int money;
        private Set<Player> applicants = new HashSet<>();

        private void addApplicantsFrom(List<Player> showedDownPlayersList, Set<Player> excludedPlayers) {
            showedDownPlayersList.stream()
                    .filter(player -> !excludedPlayers.contains(player))
                    .forEach(applicants::add);
        }
    }

}
