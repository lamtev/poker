package com.lamtev.poker.core.model;

import com.lamtev.poker.core.exceptions.IsNotEnoughMoneyException;
import com.lamtev.poker.core.exceptions.NotPositiveWagerException;
import com.lamtev.poker.core.hands.PokerHand;

import java.util.*;

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

    public void acceptBlindWagers(int smallBlindWager) {
        acceptBlindWager(players.smallBlind(), smallBlindWager);
        acceptBlindWager(players.bigBlind(), smallBlindWager * 2);
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
            List<Player> potWinners = new ArrayList<>();
            for (Player applicant : pot.applicants) {
                if (showedDownPlayers.get(applicant).equals(bestHand)) {
                    potWinners.add(applicant);
                }
            }
            for (Player player : potWinners) {
                player.addMoney(pot.money / potWinners.size());
                winners.add(player);
            }
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
            throw new IsNotEnoughMoneyException("Try to make allIn");
        }
    }

    private void buildUpPots(List<Player> showedDownPlayersList) {
        List<Player> allinners = orderedAllinners(showedDownPlayersList);
        int foldPlayersWagers = calculateFoldPlayersWagers();
        int previousAllinnerWager = 0;
        Set<Player> allinnersWhichAlreadyInPot = new HashSet<>();
        for (Player allinner : allinners) {
            Pot pot = new Pot();
            pot.addApplicantsFromListExcludeThese(showedDownPlayersList, allinnersWhichAlreadyInPot);
            pot.money = foldPlayersWagers + (allinner.wager() - previousAllinnerWager) * pot.applicants.size();
            allinnersWhichAlreadyInPot.add(allinner);
            pots.offer(pot);
            previousAllinnerWager = allinner.wager();
        }
        Pot pot = new Pot();
        pot.addApplicantsFromListExcludeThese(showedDownPlayersList, allinnersWhichAlreadyInPot);
        pot.money = allinners.isEmpty() ?
                foldPlayersWagers + wager * pot.applicants.size() :
                (wager - previousAllinnerWager) * pot.applicants.size();

        pots.offer(pot);
    }

    private PokerHand determineBestHand(final Map<Player, PokerHand> showedDownPlayers, Pot pot) {
        return showedDownPlayers.get(
                Collections.max(
                        pot.applicants,
                        new Comparator<Player>() {
                            @Override
                            public int compare(Player p1, Player p2) {
                                return showedDownPlayers.get(p1).compareTo(showedDownPlayers.get(p2));
                            }
                        }
                )
        );
    }

    private int calculateFoldPlayersWagers() {
        List<Player> players = new ArrayList<>();
        for (Player player : this.players) {
            players.add(player);
        }
        int sum = 0;
        for (Player player : players) {
            if (player.hadFold()) {
                sum += player.wager();
            }
        }
        return sum;
    }

    private List<Player> orderedAllinners(List<Player> showedDownPlayersList) {
        final List<Player> orderedAllinners = new ArrayList<>();
        for (Player player : showedDownPlayersList) {
            if (player.isAllinner()) {
                orderedAllinners.add(player);
            }
        }
        Collections.sort(orderedAllinners, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                return Integer.compare(p1.wager(), p2.wager());
            }
        });
        return orderedAllinners;
    }

    private static class Pot {
        private int money;
        private Set<Player> applicants = new HashSet<>();

        private void addApplicantsFromListExcludeThese(List<Player> showedDownPlayersList, Set<Player> excludedPlayers) {
            for (Player player : showedDownPlayersList) {
                if (!excludedPlayers.contains(player)) {
                    applicants.add(player);
                }
            }
        }
    }

}
