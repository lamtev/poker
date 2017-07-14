package com.lamtev.poker.core.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public final class Players implements Iterable<Player> {

    private static final int OFFSET_OF_SMALL_BLIND = 1;
    private static final int OFFSET_OF_BIG_BLIND = 2;
    private int dealerIndex;
    private int currentPlayerIndex = 0;
    private List<Player> players = new ArrayList<>();

    public Players(List<Player> players, String dealerId) {
        this.players = players;
        setDealer(dealerId);
    }

    public void add(Player player) {
        players.add(player);
    }

    public Player get(int index) {
        return players.get(index);
    }

    public Player get(String id) {
        for (Player player : players) {
            if (player.id().equals(id)) {
                return player;
            }
        }
        throw new NullPointerException();
    }

    public Player dealer() {
        return players.get(dealerIndex);
    }

    public Player smallBlind() {
        return players.get((dealerIndex + OFFSET_OF_SMALL_BLIND) % size());
    }

    public Player bigBlind() {
        return players.get((dealerIndex + OFFSET_OF_BIG_BLIND) % size());
    }

    public Player current() {
        return players.get(currentPlayerIndex);
    }

    public Player nextActive() {
        Player nextActivePlayer;
        while (!(nextActivePlayer = next()).isActive()) ;
        currentPlayerIndex = players.indexOf(nextActivePlayer);
        return current();
    }

    public Player nextActiveNonAllinner() {
        do {
            nextActive();
        } while (current().stack() == 0);
        return current();
    }

    public Player nextAfterBigBlind() {
        currentPlayerIndex = players.indexOf(bigBlind());
        do {
            ++currentPlayerIndex;
            currentPlayerIndex %= size();
        } while (!current().isActive());
        return current();
    }

    public Player nextNonAllinnerAfterDealer() {
        currentPlayerIndex = dealerIndex;
        do {
            ++currentPlayerIndex;
            currentPlayerIndex %= size();
        } while (!current().isActive() ||current().isAllinner());
        return current();
    }

    public Player nextActiveAfterDealer() {
        currentPlayerIndex = dealerIndex;
        do {
            ++currentPlayerIndex;
            currentPlayerIndex %= size();
        } while (!current().isActive());
        return current();
    }

    public void setLatestAggressor(Player latestAggressor) {
        currentPlayerIndex = players.indexOf(latestAggressor);
    }

    public int size() {
        return players.size();
    }

    public int activePlayersNumber() {
        return (int) players.stream()
                .filter(Player::isActive)
                .count();
    }

    public int allinnersNumber() {
        return (int) players.stream()
                .filter(Player::isAllinner)
                .count();
    }

    public int activeNonAllinnersNumber() {
        return (int) players.stream()
                .filter(Player::isActiveNonAllinner)
                .count();
    }

    public int activeNonAllinnersWithSameWagerNumber(int wager) {
        return (int) players.stream()
                .filter(Player::isActiveNonAllinner)
                .filter(player -> player.wager() == wager)
                .count();
    }

    public boolean isEmpty() {
        return players.isEmpty();
    }

    @Override
    public Iterator<Player> iterator() {
        return players.iterator();
    }

    @Override
    public void forEach(Consumer<? super Player> action) {
        players.forEach(action);
    }

    private void setDealer(String dealerId) {
        Player dealer = players.stream()
                .filter(player -> player.id().equals(dealerId))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        dealerIndex = players.indexOf(dealer);
    }

    private Player next() {
        ++currentPlayerIndex;
        currentPlayerIndex %= size();
        return current();
    }

}
