package com.lamtev.poker.core.model;

import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.states.exceptions.IsNotEnoughMoneyException;
import com.lamtev.poker.core.states.exceptions.NotPositiveWagerException;

import java.util.*;
import java.util.stream.Collectors;

public final class Bank {

    private Queue<Pot> pots = new ArrayDeque<>();
    private int money;
    private int wager;
    private Players players;

    public Bank(Players players) {
        this.players = players;
    }

    public int money() {
        return money;
    }

    public int currentWager() {
        return wager;
    }

    public void acceptBlindWagers(int smallBlindSize) {
        acceptBlindWager(players.smallBlind(), smallBlindSize);
        acceptBlindWager(players.bigBlind(), smallBlindSize * 2);
    }

    public void acceptCall(Player player) throws IsNotEnoughMoneyException {
        if (players.smallBlind().wager() == 0 || players.bigBlind().wager() == 0) {
            throw new RuntimeException("Can't accept call from player when blinds are not set");
        }
        int moneyTakingFromPlayer = wager - player.wager();
        if (moneyTakingFromPlayer == player.stack()) {
            acceptAllIn(player);
            return;
        }
        validateTakingMoneyFromPlayer(player, moneyTakingFromPlayer);
        money += player.takeMoney(moneyTakingFromPlayer);
    }

    public void acceptRaise(int additionalWager, Player player) throws
            IsNotEnoughMoneyException, NotPositiveWagerException {
        if (additionalWager <= 0) {
            throw new NotPositiveWagerException();
        }
        int moneyTakingFromPlayer = wager + additionalWager - player.wager();
        if (moneyTakingFromPlayer == player.stack()) {
            acceptAllIn(player);
            return;
        }
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
        int potsMoney = 0;
        while (!pots.isEmpty()) {
            Pot pot = pots.poll();
            PokerHand winnerPokerHand = showedDownPlayers.get(
                    Collections.max(
                            pot.applicants,
                            Comparator.comparing(showedDownPlayers::get)
                    )
            );
            List<Player> potWinners = pot.applicants.stream()
                    .filter(player -> showedDownPlayers.get(player).equals(winnerPokerHand))
                    .collect(Collectors.toList());
            potWinners.forEach(player -> {
                player.addMoney(pot.money / potWinners.size());
                winners.add(player);
            });
            potsMoney += pot.money;
        }
        System.out.println(money + " == " + potsMoney);
        assert money - potsMoney < 0.5;
        money = 0;
        return winners;
    }

    private void buildUpPots(List<Player> showedDownPlayersList) {
        //TODO refactor large method
        List<Player> allinners = new ArrayList<>();
        showedDownPlayersList.forEach(player -> {
            if (player.isAllinner()) {
                allinners.add(player);
            }
        });
        allinners.sort(Comparator.comparingInt(Player::wager));
        List<Player> foldPlayers = new ArrayList<>();
        players.forEach(player -> {
            if (!player.isActive()) {
                foldPlayers.add(player);
            }
        });
        int foldPlayersWagers = foldPlayers.stream()
                .map(Player::wager)
                .mapToInt(Number::intValue)
                .sum();
        int previousAllinnerWager = 0;
        Set<Player> excludedPlayers = new HashSet<>();
        for (Player allinner : allinners) {
            Pot pot = new Pot();
            showedDownPlayersList.stream()
                    .filter(player -> !excludedPlayers.contains(player))
                    .forEach(pot.applicants::add);
            pot.money = foldPlayersWagers + (allinner.wager() - previousAllinnerWager) * pot.applicants.size();
            excludedPlayers.add(allinner);
            pots.offer(pot);
            previousAllinnerWager = allinner.wager();
        }
        Pot pot = new Pot();
        showedDownPlayersList.stream()
                .filter(player -> !excludedPlayers.contains(player))
                .forEach(pot.applicants::add);
        pot.money = allinners.isEmpty() ?
                foldPlayersWagers + wager * pot.applicants.size() :
                (wager - previousAllinnerWager) * pot.applicants.size();

        pots.offer(pot);
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
            throw new IsNotEnoughMoneyException("Try to make allIn");
        }
    }

    private static class Pot {
        private int money;
        private Set<Player> applicants = new HashSet<>();
    }

}
