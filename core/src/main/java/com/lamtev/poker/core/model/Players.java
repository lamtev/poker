package com.lamtev.poker.core.model;

import java.util.*;
import java.util.function.Consumer;

public final class Players implements Iterable<Player> {

    private static final int OFFSET_OF_SMALL_BLIND = 1;
    private static final int OFFSET_OF_BIG_BLIND = 2;
    private int dealerIndex;
    private int currentPlayerIndex = 0;
    private final List<Player> players = new ArrayList<>();

    public Players(Collection<Player> players, String dealerId) {
        this.players.addAll(players);
        dealerIndex = this.players.indexOf(get(dealerId));
        Collections.rotate(this.players, size() - 1 - dealerIndex);
        dealerIndex = size() - 1;
    }

    public void add(Player player) {
        players.add(player);
    }

    public Player get(String id) {
        return players.stream()
                .filter(player -> player.id().equals(id))
                .findFirst()
                .orElseThrow(NullPointerException::new);
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
        while ((nextActivePlayer = next()).hadFold()) ;
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
        } while (current().hadFold());
        return current();
    }

    public Player nextNonAllinnerAfterDealer() {
        currentPlayerIndex = dealerIndex;
        do {
            ++currentPlayerIndex;
            currentPlayerIndex %= size();
        } while (current().hadFold() || current().isAllinner());
        return current();
    }

    public Player nextActiveAfterDealer() {
        currentPlayerIndex = dealerIndex;
        do {
            ++currentPlayerIndex;
            currentPlayerIndex %= size();
        } while (current().hadFold());
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

    @Override
    public String toString() {
        return "Players{" +
                "dealerIndex=" + dealerIndex +
                ", currentPlayerIndex=" + currentPlayerIndex +
                ", players=" + players +
                '}';
    }

    private Player next() {
        ++currentPlayerIndex;
        currentPlayerIndex %= size();
        return current();
    }

}
